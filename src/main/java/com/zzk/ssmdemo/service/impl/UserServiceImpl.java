package com.zzk.ssmdemo.service.impl;

import com.zzk.ssmdemo.dao.UserDao;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.UserException;
import com.zzk.ssmdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author situliang
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisCacheTemplate;

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User getUserById(Integer id) {
        if (id < 1) {
            throw new UserException(ResultEnum.PARAMETER_ERROR);
        }
        try {
            User user = (User) redisCacheTemplate.opsForValue().get(PREFIX + "_" + id);
            if (user == null) {
                user = userDao.selectByPrimaryKey(id);
                redisCacheTemplate.opsForValue().set(PREFIX + "_" + id, user);
            }
            return user;
        } catch (Exception e) {
            throw new UserException(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertUser(User user) {
        try {
            Integer effectNum = userDao.insertSelective(user);
            logger.info("insert user id is:" + user.getUserId());
            return effectNum;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        try {
            Integer effectNum = userDao.updateUserById(user);
            if (effectNum > 0) {
                redisCacheTemplate.delete(PREFIX + "_" + user.getUserId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ResultEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        try {
            Integer effectNum = userDao.deleteUserById(id);
            if (effectNum > 0) {
                redisCacheTemplate.delete(PREFIX + "_" + id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UserException(ResultEnum.UNKNOWN_ERROR);
        }
    }


}
