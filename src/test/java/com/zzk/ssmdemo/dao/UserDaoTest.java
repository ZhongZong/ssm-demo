package com.zzk.ssmdemo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName UserDaoTest
 * @Description: 用户DAO测试类
 * @Author situliang
 * @Date 2019/7/17
 * @Version V1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetAll(){
        System.out.println(userDao.getAllUser());
    }



}
