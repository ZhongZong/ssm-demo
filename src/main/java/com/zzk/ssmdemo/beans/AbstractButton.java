package com.zzk.ssmdemo.beans;

/**
 * @ClassName AbstractButton
 * @Description: 抽象的菜单按钮,所有菜单都继承这个类
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public abstract class AbstractButton {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractButton(String name) {
        this.name = name;
    }
}
