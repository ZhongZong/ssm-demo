package com.zzk.ssmdemo.dao;

import com.zzk.ssmdemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author situliang
 */
@Repository("userDao")
public interface UserDao {


    /**
     * 根据主键查询用户信息
     *
     * @param id 主键ID
     * @return 用户对象
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 获取所有的关注用户
     *
     * @return 关注用户列表
     */
    List<User> getAllUser();

    /**
     * 查询是否存在指定ID的用户
     *
     * @param openid
     * @return
     */
    Integer countByOpenId(String openid);

    /**
     * 新增一个用户
     *
     * @param user 用户
     * @return 对象主键值
     */
    Integer insertSelective(User user);

    /**
     * 更新一个用户
     *
     * @param user 更新的用户，用户ID必须有值
     * @return 影响行数
     */
    int updateUserById(User user);

    /**
     * 根据用户微信的open更新一个用户
     *
     * @param user 用户
     * @return 影响行数
     */
    int updateUserByOpenId(User user);

    /**
     * 根据ID删除用户
     *
     * @param userId 删除用户ID
     * @return 影响行数
     */
    int deleteUserById(Integer userId);

}
