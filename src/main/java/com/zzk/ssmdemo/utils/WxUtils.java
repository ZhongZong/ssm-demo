package com.zzk.ssmdemo.utils;

import com.zzk.ssmdemo.beans.WeiXin;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * @ClassName WxUtils
 * @Description: 微信的工具类
 * @Author situliang
 * @Date 2019/7/9
 * @Version V1.0
 **/

public class WxUtils {

    /**
     * 验证签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param signature 微信加密签名
     * @return 是否成功
     */
    public static boolean check(String timestamp, String nonce, String signature){
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{timestamp,nonce, WeiXin.getToken()};
        Arrays.sort(strs);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        String mysig = DigestUtils.sha1Hex(str);
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mysig.equals(signature);
    }


}
