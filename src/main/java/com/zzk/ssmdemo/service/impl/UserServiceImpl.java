package com.zzk.ssmdemo.service.impl;

import com.zzk.ssmdemo.dao.UserDao;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.CommonException;
import com.zzk.ssmdemo.service.AccessTokenService;
import com.zzk.ssmdemo.service.UserService;
import com.zzk.ssmdemo.utils.PureNetUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author situliang
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisCacheTemplate;

    @Autowired
    private AccessTokenService accessTokenService;

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User getUserById(Integer id) {
        if (id < 1) {
            throw new CommonException(ResultEnum.PARAMETER_ERROR);
        }
        try {
            User user = (User) redisCacheTemplate.opsForValue().get(PREFIX + "_" + id);
            if (user == null) {
                user = userDao.selectByPrimaryKey(id);
                redisCacheTemplate.opsForValue().set(PREFIX + "_" + id, user);
            }
            return user;
        } catch (Exception e) {
            throw new CommonException(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    public void getUserInfo() {
        List<User> userList = userDao.getAllUser();
        String token = accessTokenService.getAccessToken();
        logger.info("获取用户信息前得到的token:{}", token);
        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" +
                        token + "&openid=" + user.getOpenid() + "&lang=zh_CN";
                String res = PureNetUtil.get(url);
                logger.info("获取用户:{}的基本信息为:{}", user.getOpenid(), res);
            }
        }
    }

    @Override
    public List<User> getUsers() {
        return userDao.getAllUser();
    }

    @Override
    public Integer countByOpenId(String openid) {
        return userDao.countByOpenId(openid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertUser(Map<String, String> requestUser) {
        Integer effectNum;
        try {
            User user = new User();
            user.setOpenid(requestUser.get("FromUserName"));
            user.setActive(1);
            user.setOperateTime(new Date());
            if (countByOpenId(user.getOpenid()) > 0) {
                effectNum = userDao.updateUserByOpenId(user);
            } else {
                effectNum = userDao.insertSelective(user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommonException(ResultEnum.UNKNOWN_ERROR);
        }
        return effectNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        try {
            userDao.updateUserById(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommonException(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String openid) {
        User user = new User();
        user.setActive(0);
        user.setOperateTime(new Date());
        user.setOpenid(openid);
        try {
            userDao.updateUserByOpenId(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CommonException(ResultEnum.UNKNOWN_ERROR);
        }
    }


}
