package com.zzk.ssmdemo.service;

import java.util.Map;

/**
 * 接收事件推送
 */
public interface EventService {

    String delEvent(Map<String, String> requestMap);
}
