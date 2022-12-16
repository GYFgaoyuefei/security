package com.wh.dsy.starter.security.service;

import com.wh.dsy.starter.security.mapper.MenuMapper;
import com.wh.dsy.starter.security.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }
}
