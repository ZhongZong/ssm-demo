package com.zzk.ssmdemo.dao;

import com.zzk.ssmdemo.entity.User;
import org.springframework.stereotype.Repository;

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
     * 新增一个用户
     *
     * @param user 用户
     * @return 对象主键值
     */
    Integer insertSelective(User user);

}
