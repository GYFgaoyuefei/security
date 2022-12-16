package com.wh.dsy.starter.security.mapper;

import com.wh.dsy.starter.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuMapper {
    List<Menu> getAllMenu();
}
