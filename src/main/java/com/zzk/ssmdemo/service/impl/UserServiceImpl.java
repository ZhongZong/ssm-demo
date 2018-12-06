package com.zzk.ssmdemo.service.impl;

import com.zzk.ssmdemo.dao.UserDao;
import com.zzk.ssmdemo.entity.User;
import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.UserException;
import com.zzk.ssmdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author situliang
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisCacheTemplate;

    @Override
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
    @Transactional
    public Integer insertUser(User user) {
        try {
            return userDao.insertSelective(user);
        } catch (Exception e) {
            throw new UserException(ResultEnum.UNKNOWN_ERROR);
        }
    }
}
