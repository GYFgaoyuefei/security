package com.wh.dsy.starter.security.entity;

import java.io.Serializable;

/**
 * 角色
 */
public class Role implements Serializable {
    private String id;
    private String name;
    private String nameZh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}
