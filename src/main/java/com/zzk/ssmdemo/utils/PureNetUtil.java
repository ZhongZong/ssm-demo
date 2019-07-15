package com.zzk.ssmdemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @ClassName PureNetUtil
 * @Description: 网络访问工具类, 来自聚合数据
 * @Author situliang
 * @Date 2019/7/11
 * @Version V1.0
 **/

public class PureNetUtil {

    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(PureNetUtil.class);

    /**
     * get方法直接调用post方法
     *
     * @param url 网络地址
     * @return 返回网络数据
     */
    public static String get(String url) {
        return post(url, null);
    }

    /**
     * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
     *
     * @param url   网络地址
     * @param param 请求参数键值对
     * @return 返回读取数据
     */
    public static String post(String url, Map<String, String> param) {
        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            StringBuffer sb = null;
            if (param != null) {
                // 如果请求参数不为空
                sb = new StringBuffer();
                /*
                 * A URL connection can be used for input and/or output. Set the
                 * DoOutput flag to true if you intend to use the URL connection
                 * for output, false if not. The default is false.
                 */
                // 默认为false,post方法需要写入参数,设定true
                conn.setDoOutput(true);
                // 设定post方法,默认get
                conn.setRequestMethod("POST");
                // 获得输出流
                OutputStream out = conn.getOutputStream();
                // 对输出流封装成高级输出流
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                // 将参数封装成键值对的形式
                for (Map.Entry<String, String> s : param.entrySet()) {
                    sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
                }
                // 将参数通过输出流写入
                writer.write(sb.deleteCharAt(sb.toString().length() - 1).toString());
                writer.close();// 一定要关闭,不然可能出现参数不全的错误
                sb = null;
            }
            conn.connect();// 建立连接
            sb = new StringBuffer();
            // 获取连接状态码
            int recode = conn.getResponseCode();
            BufferedReader reader = null;
            if (recode == 200) {
                // Returns an input stream that reads from this open connection
                // 从连接中获取输入流
                InputStream in = conn.getInputStream();
                // 对输入流进行封装
                reader = new BufferedReader(new InputStreamReader(in));
                String str = null;
                sb = new StringBuffer();
                // 从输入流中读取数据
                while ((str = reader.readLine()) != null) {
                    sb.append(str).append(System.getProperty("line.separator"));
                }
                // 关闭输入流
                reader.close();
                if (sb.toString().length() == 0) {
                    return null;
                }
                return sb.toString().substring(0,
                        sb.toString().length() - System.getProperty("line.separator").length());
            }
        } catch (Exception e) {
            log.error("调用远程Post接口出现异常,异常信息:{}", e.getLocalizedMessage());
            return null;
        } finally {
            if (conn != null) {
                // 关闭连接
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 使用post json数据的方式调用远程的接口
     *
     * @param url  远程接口地址
     * @param data 请求的json字符串
     * @return 返回的数据
     */
    public static String postJson(String url, String data) {
        try {
            URL urlObject = new URL(url);
            URLConnection connection = urlObject.openConnection();
            //要发送数据出去,必须要设置为可发送数据状态
            connection.setDoOutput(true);
            //获取输出流
            OutputStream os = connection.getOutputStream();
            //写出数据
            os.write(data.getBytes());
            os.close();
            //获取输入流
            InputStream inputStream = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = inputStream.read(b)) != -1) {
                stringBuilder.append(new String(b, 0, len));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("调用远程postJson接口出现异常,异常信息:{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
}
