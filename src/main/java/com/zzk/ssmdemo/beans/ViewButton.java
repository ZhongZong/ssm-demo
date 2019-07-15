package com.zzk.ssmdemo.beans;

/**
 * @ClassName ViewButton
 * @Description: view按钮
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public class ViewButton extends AbstractButton{

    private String type = "view";

    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ViewButton(String name, String url) {
        super(name);
        this.url = url;
    }
}
