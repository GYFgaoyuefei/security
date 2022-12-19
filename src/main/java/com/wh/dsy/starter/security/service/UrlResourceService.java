package com.wh.dsy.starter.security.service;

import com.wh.dsy.starter.security.mapper.UrlResourceMapper;
import com.wh.dsy.starter.security.entity.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlResourceService {
    @Autowired
    UrlResourceMapper urlResourceMapper;

    public List<UrlResource> getAllUrlResource() {
        return urlResourceMapper.getAllUrlResource();
    }
}
