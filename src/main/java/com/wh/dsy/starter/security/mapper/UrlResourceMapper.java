package com.wh.dsy.starter.security.mapper;

import com.wh.dsy.starter.security.entity.UrlResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UrlResourceMapper {
    List<UrlResource> getAllUrlResource();
}
