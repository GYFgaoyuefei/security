package com.wh.dsy.starter.security.config.handler;

import com.wh.dsy.starter.security.entity.User;
import com.wh.dsy.starter.security.utils.CommonUtils;
import com.wh.dsy.starters.redis.common.Constants;
import com.wh.dsy.starters.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if (Objects.nonNull(principal)) {
                if (principal instanceof User) {
                    redisService.deleteObject(Constants.TOKEN_CACHE_KEY + ((User) principal).getToken());
                    log.info("时间:{},IP:{},用户名{}注销成功", CommonUtils.getCurrentDateTime(), CommonUtils.getIPAddress(request), ((User) principal).getUsername());
                }
            }
        }
    }
}
