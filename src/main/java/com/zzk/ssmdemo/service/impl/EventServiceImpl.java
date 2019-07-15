package com.zzk.ssmdemo.service.impl;

import com.zzk.ssmdemo.beans.BaseMessage;
import com.zzk.ssmdemo.service.EventService;
import com.zzk.ssmdemo.service.UserService;
import com.zzk.ssmdemo.utils.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName EventServiceImpl
 * @Description: 接收事件推送实现类
 * @Author situliang
 * @Date 2019/7/15
 * @Version V1.0
 **/

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private UserService userService;

    @Override
    public String delEvent(Map<String, String> requestMap) {
        String event = requestMap.get("Event");
        String re = "";
        switch (event) {
            case "subscribe":
                // 新订阅的用户
                userService.insertUser(requestMap);
                re = WxUtils.delSubscribe(requestMap);
                break;
            case "unsubscribe":
                // 新取消订阅的用户
                userService.deleteUser(requestMap.get("FromUserName"));
                break;
            case "CLICK":
                //click菜单点击事件
                re = WxUtils.delClick(requestMap);
                break;
            case "VIEW":
                //view菜单点击事件

                break;
            default:
                break;
        }

        return re;
    }


}
