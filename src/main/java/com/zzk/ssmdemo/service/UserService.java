package com.zzk.ssmdemo.service;

import com.zzk.ssmdemo.entity.User;

/**
 * @author situliang
 */
public interface UserService {

    static final String PREFIX = "user";

    /**
     * 根据用户主键ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Integer id);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 0:失败,1:成功
     */
    Integer insertUser(User user);
}
