package com.zzk.ssmdemo.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * @ClassName TextMessage
 * @Description: 文本消息类
 * @Author situliang
 * @Date 2019/7/10
 * @Version V1.0
 **/


@XStreamAlias("xml")
public class TextMessage extends BaseMessage {

    @XStreamAlias("Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextMessage(Map<String, String> requestMap, String content){
        super(requestMap);
        //设置文本消息的msgType为text
        this.setMsgType("text");

        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "content='" + content + '\'' +
                '}';
    }
}
