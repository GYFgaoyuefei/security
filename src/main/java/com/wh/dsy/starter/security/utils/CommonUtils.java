package com.wh.dsy.starter.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 常用工具类
 */
public class CommonUtils {

    /**
     * 存储登陆用户名
     */
    public static ThreadLocal<String> usernameThreadLocal = new ThreadLocal<String>();

    /**
     * 标准时间格式
     */
    public static final String STANDARD = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return formatDateToStr(new Date(), STANDARD);
    }


    public static String formatDateToStr(Date date, String pattern) {
        SimpleDateFormat sformat = new SimpleDateFormat(pattern);
        return sformat.format(date);
    }


    /**
     * 返回信息设置
     *
     * @param response
     * @param statusCode
     * @param responseMsg
     * @throws IOException
     */
    public static void response(HttpServletResponse response, int statusCode, String responseMsg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(statusCode);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", statusCode);
        resp.put("msg", responseMsg);
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(resp);
        response.getWriter().write(s);
    }
}
