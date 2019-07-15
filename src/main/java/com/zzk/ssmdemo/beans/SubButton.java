package com.zzk.ssmdemo.beans;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName SubButton
 * @Description: 二级菜单
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public class SubButton extends AbstractButton{

    private List<AbstractButton> sub_button = Lists.newArrayList();

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }

    public SubButton(String name) {
        super(name);
    }
}
