package com.wh.dsy.starter.security.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 资源
 */
public class Menu implements Serializable {
    private Integer id;
    private String pattern;
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}