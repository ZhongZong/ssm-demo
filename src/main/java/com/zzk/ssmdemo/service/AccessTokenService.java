package com.zzk.ssmdemo.service;

/**
 * @author situliang
 */
public interface AccessTokenService {

    static final String PREFIX = "wx_access_token";

    String getAccessToken();

}
