package com.zzk.ssmdemo.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * @ClassName MusicMessage
 * @Description: 音乐消息对象
 * @Author situliang
 * @Date 2019/7/10
 * @Version V1.0
 **/
@XStreamAlias("xml")
public class MusicMessage extends BaseMessage {

    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public MusicMessage(Map<String, String> requestMap, Music music) {
        super(requestMap);
        this.setMsgType("music");
        this.music = music;
    }
}
