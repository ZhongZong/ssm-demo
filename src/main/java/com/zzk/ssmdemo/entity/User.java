package com.zzk.ssmdemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author situliang
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1545427818902534951L;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 微信ID
     */
    private String openid;

    /**
     * 是否关注中
     */
    private Integer active;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 天气预警的城市
     */
    private String city;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", openid='" + openid + '\'' +
                ", active=" + active +
                ", operateTime=" + operateTime +
                ", city='" + city + '\'' +
                '}';
    }
}
