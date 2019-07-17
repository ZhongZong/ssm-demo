package com.zzk.ssmdemo.service;

import com.zzk.ssmdemo.entity.User;

import java.util.List;
import java.util.Map;

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
     * 获取所有的关注用户列表
     *
     * @return 关注用户的列表
     */
    List<User> getUsers();

    /**
     * 查询是否存在指定微信openid的用户
     *
     * @param openid
     * @return
     */
    Integer countByOpenId(String openid);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 0:失败,1:成功
     */
    Integer insertUser(Map<String, String> user);

    /**
     * 更新用户
     *
     * @param user 用户对象
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户的openid
     */
    void deleteUser(String id);
}
