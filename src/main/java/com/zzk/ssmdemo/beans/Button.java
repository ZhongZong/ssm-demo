package com.zzk.ssmdemo.beans;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName Button
 * @Description: 微信菜单按钮
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public class Button {

    private List<AbstractButton> button = Lists.newArrayList();

    public List<AbstractButton> getButton() {
        return button;
    }

    public void setButton(List<AbstractButton> button) {
        this.button = button;
    }
}
