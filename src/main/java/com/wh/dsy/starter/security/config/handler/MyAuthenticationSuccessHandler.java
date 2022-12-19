package com.wh.dsy.starter.security.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.dsy.starter.security.entity.User;
import com.wh.dsy.starter.security.utils.CommonUtils;
import com.wh.dsy.starter.security.utils.JwtTokenUtils;
import com.wh.dsy.starters.redis.common.Constants;
import com.wh.dsy.starters.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", HttpStatus.OK.value());
        resp.put("msg", "登录成功!");
        Object principal = authentication.getPrincipal();
        ((User) principal).setPassword(null);
        String token = JwtTokenUtils.generateToken(authentication);
        ((User) principal).setToken(token);
        resp.put("user", principal);
        redisService.setCacheObject(Constants.TOKEN_CACHE_KEY + token, principal, Constants.EXPIRE_TIME, TimeUnit.MILLISECONDS);
        log.info("时间:{},IP:{},登陆用户名{}登录成功", CommonUtils.getCurrentDateTime(), CommonUtils.getIPAddress(request), CommonUtils.usernameThreadLocal.get());
        CommonUtils.usernameThreadLocal.remove();
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(resp);
        response.getWriter().write(s);
    }
}
