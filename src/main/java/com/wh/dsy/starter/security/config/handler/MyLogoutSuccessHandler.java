package com.wh.dsy.starter.security.config.handler;

import com.wh.dsy.starter.security.utils.CommonUtils;
import com.wh.dsy.starter.security.utils.JwtTokenUtils;
import com.wh.dsy.starters.redis.common.Constants;
import com.wh.dsy.starters.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = JwtTokenUtils.getToken(request);
        if (StringUtils.isBlank(token)) {
            CommonUtils.response(response, HttpStatus.UNAUTHORIZED.value(), "注销失败：未认证用户！");
        } else {
            String username = JwtTokenUtils.getUsernameFromToken(token);
            String userId = JwtTokenUtils.getUserIdFromToken(token);
            String key = Constants.TOKEN_CACHE_KEY + userId;
            Object cacheObject = redisService.getCacheObject(key);
            if (Objects.nonNull(cacheObject)) {
                if (token.equals(cacheObject.toString())){
                    redisService.deleteObject(key);
                    log.info("时间:{},IP:{},用户名{}注销成功", CommonUtils.getCurrentDateTime(), CommonUtils.getIPAddress(request), username);
                    CommonUtils.response(response, HttpStatus.OK.value(), "注销成功！");
                }else {
                    CommonUtils.response(response, HttpStatus.UNAUTHORIZED.value(), "注销失败：token无效！");
                }
            } else {
                CommonUtils.response(response, HttpStatus.UNAUTHORIZED.value(), "注销失败：未登录用户！");
            }

        }

    }
}
