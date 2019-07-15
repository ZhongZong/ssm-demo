package com.zzk.ssmdemo.beans;

/**
 * @ClassName ClickButton
 * @Description: 点击按钮
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public class ClickButton extends AbstractButton{

    private String type = "click";

    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ClickButton(String name, String key) {
        super(name);
        this.key = key;
    }
}
