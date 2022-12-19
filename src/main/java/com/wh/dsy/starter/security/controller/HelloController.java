package com.wh.dsy.starter.security.controller;

import com.wh.dsy.starter.security.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
public class HelloController {
    @GetMapping("/admin/hello")
    public String admin() {
        System.out.println(SecurityUtils.getUsername());
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        System.out.println(SecurityUtils.getUsername());
        return "hello user";
    }

    @GetMapping("/guest/hello")
    public String guest() {
        System.out.println(SecurityUtils.getUsername());
        return "hello guest";
    }


    @GetMapping("/hello")
    public String hello() {
        System.out.println(SecurityUtils.getUsername());
        return "hello";
    }

    @GetMapping("/hello1")
    public String hello1() {
        System.out.println(SecurityUtils.getUsername());
        return "hello1";
    }
}
