package com.wh.dsy.starter.security.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.dsy.starter.security.utils.CommonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录过滤器
 */
public class MyLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //必须是post请求
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        /**
         * 如果是json格式的直接在当前方法中处理
         */
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)) {

            Map<String, String> userInfo = new HashMap<>();
            try {
                userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = userInfo.get(getUsernameParameter());
                CommonUtils.usernameThreadLocal.set(username);
                String password = userInfo.get(getPasswordParameter());
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
                return authenticate;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //非json格式数据直接调用父类方法处理
        CommonUtils.usernameThreadLocal.set(super.obtainUsername(request));
        return super.attemptAuthentication(request, response);
    }
}
