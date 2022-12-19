package com.wh.dsy.starter.security;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class SecurityApplicationTests {

    @Test
    void contextLoads() {
    }



    public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
    }
}
