package com.wh.dsy.starter.security.config.handler;

import com.wh.dsy.starter.security.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.error("时间:{},IP:{},登陆用户名{}登录失败,异常信息:{}", CommonUtils.getCurrentDateTime(), CommonUtils.getIPAddress(request), CommonUtils.usernameThreadLocal.get(), exception.getMessage());
        CommonUtils.usernameThreadLocal.remove();
        String responseMsg = "";
        if ("Bad credentials".equalsIgnoreCase(exception.getMessage())) {
            responseMsg = "登录失败：用户名或密码错误！";
        } else {
            responseMsg = exception.getMessage();
        }
        CommonUtils.response(response, 401, responseMsg);
    }
}
