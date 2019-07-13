package com.zzk.ssmdemo.controller;

import com.zzk.ssmdemo.enums.ResultEnum;
import com.zzk.ssmdemo.exception.UserException;
import com.zzk.ssmdemo.utils.WxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description: 微信公众号接入的入口控制器
 * @Author situliang
 * @Date 2019/7/9
 * @Version V1.0
 **/
@Controller
@RequestMapping("/index")
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    /**
     * 入口方法
     *
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public void wx(HttpServletRequest request, HttpServletResponse response) {
        // 拿到微信请求过来的数据
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //校验签名
        if (WxUtils.check(timestamp, nonce, signature)) {
            log.info("接入成功");
            try {
                PrintWriter out = response.getWriter();
                //原样返回echostr
                out.print(echostr);
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("微信接入出现异常,异常信息:{}", e.getMessage());
                throw new UserException(ResultEnum.RESPONSE_ERROR);
            }
        } else {
            log.info("接入失败");
        }
    }

    /**
     * 接收消息和事件推送
     *
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public void receive(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf8");
            response.setCharacterEncoding("utf8");
            Map<String, String> requestMap = WxUtils.parseRequest(request.getInputStream());
            String respXml =  WxUtils.getResponse(requestMap);
            // log.info(respXml);
            PrintWriter out = response.getWriter();
            out.print(respXml);
            out.flush();
            out.close();
            //log.info("解析后的消息:{}", requestMap.toString());
        } catch (Exception e) {
            log.error("解析微信请求出现异常:{}", e.getMessage());
            throw new UserException(ResultEnum.PARAMETER_ERROR);
        }
    }


}
