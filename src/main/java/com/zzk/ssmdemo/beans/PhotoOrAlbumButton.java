package com.zzk.ssmdemo.beans;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName PhotoOrAlbumButton
 * @Description: 拍照或上传图片
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

public class PhotoOrAlbumButton extends AbstractButton{

    private String type = "pic_photo_or_album";
    private String key;
    private List<AbstractButton> sub_button = Lists.newArrayList();

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

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }

    public PhotoOrAlbumButton(String name, String key) {
        super(name);
        this.key = key;
    }
}
