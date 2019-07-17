package com.zzk.ssmdemo.service;

/**
 * 菜单接口类
 * @author situliang
 */
public interface MenuService {

    /**
     * 设置菜单
     */
    void addMenu();

    /**
     * 设置公众号所属的行业
     */
    void setIndustry();

    /**
     * 获取该微信公众号设置的行业信息
     *
     * @return 行业信息json串
     */
    String getIndustry();
}
