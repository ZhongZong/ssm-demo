# ssm-demo 一个简单的SSM项目
**整合了SpringBoot2.1 + Redis(Lettuce5.1) + Mybatis3.4**

----------

# 部署步骤
1. 修改resources下的application.yml中数据库连接配置和Redis连接配置
2. 在数据库中创建一个表名:user的表(也可以为其他,但是注意修改UserMapper中的表名),
字段仅有user_id,username
3. 注意端口号和context-path,在UserController中为整个类也加了RequestMapping注解
4. 如步骤3中的内容未修改,且将步骤1,2中的内容修改为本机内容后运行项目
# 相关访问URL
- 新增用户URL(POST请求)->http://locahost:8080/user/user/insertuser/用户名(数字或字母)
- 查询用户URL(GET请求)->http://locahost:8080/user/user/getuser/用户ID(数字)
- 删除用户URL(DELETE请求)->http://locahost:8080/user/user/deleteuser/用户ID(数字)
- 更新用户URL(PUT请求)->http://locahost:8080/user/user/updateuser

## 杂项
- 数据表的SQL语句
``` 
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
- Java环境使用的是JDK1.8