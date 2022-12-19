package com.wh.dsy.starter.security.config.filters;

import com.wh.dsy.starter.security.utils.SecurityUtils;
import com.wh.dsy.starters.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证检查过滤器
 * 访问接口的时候，登录认证检查过滤器 JwtAuthenticationFilter 会拦截请求校验令牌和登录状态，并根据情况设置登录状态。
 */
@Slf4j
@Component
public class MyJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!request.getServletPath().startsWith("/actuator")) {
            log.info("===================" + request.getServletPath() + "===================");
        }
        // 获取token, 并检查登录状态
        SecurityUtils.checkAuthentication(request, redisService);
        chain.doFilter(request, response);
    }
}
