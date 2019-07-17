package com.zzk.ssmdemo.utils;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import com.baidu.aip.ocr.AipOcr;
import com.zzk.ssmdemo.beans.WeiXin;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @ClassName BdSdkUtil
 * @Description: 百度API的单例工厂
 * @Author situliang
 * @Date 2019/7/17
 * @Version V1.0
 **/

public class BdSdkUtil {

    /**
     * 通用图文识别的接口
     */
    private static AipOcr aipOcr = new AipOcr(WeiXin.getBaiduAppId(), WeiXin.getBaiduAppKey(),
            WeiXin.getBaiduSecretKey());

    /**
     * 图片审核的接口
     */
    private static AipContentCensor aipContentCensor = new AipContentCensor(WeiXin.getBdImageDetectAppId(),
            WeiXin.getBdImageDetectAppKey(), WeiXin.getBdImageDetectSecretKey());

    static {
        // 可选：设置网络连接参数
        aipOcr.setConnectionTimeoutInMillis(2000);
        aipOcr.setSocketTimeoutInMillis(60000);

        aipContentCensor.setConnectionTimeoutInMillis(2000);
        aipContentCensor.setSocketTimeoutInMillis(60000);
    }

    private BdSdkUtil() {

    }

    /**
     * 通用图文识别
     *
     * @param url 图片的网络地址
     * @return 检测结果
     */
    public static String basicGeneralUrl(String url) {
        JSONObject jsonObject = aipOcr.basicGeneralUrl(url, new HashMap<String, String>());
        return jsonObject.toString();
    }

    /**
     * 通用的图片审核
     *
     * @param url 图片的网络地址
     * @return 审核结果
     */
    public static String imageCensorUserDefined(String url) {
        JSONObject res = aipContentCensor.imageCensorUserDefined(url, EImgType.URL, null);
        return res.toString();
    }
}
