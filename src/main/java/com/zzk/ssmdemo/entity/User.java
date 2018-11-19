package com.zzk.ssmdemo.entity;

import java.io.Serializable;

/**
 * @author situliang
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1545427818902534951L;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String username;

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
