package com.wh.dsy.starter.security.config;

import com.wh.dsy.starter.security.entity.UrlResource;
import com.wh.dsy.starter.security.service.UrlResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 自定义权限元数据获取业务类
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    UrlResourceService urlResourceService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @param object 受保护的对象
     * @return 返回为访问受保护对象所需要的权限
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //先提取出当前的URL地址
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        //查询出所有菜单数据，每条数据都包含着访问该条记录所需要的权限
        List<UrlResource> allUrlResource = urlResourceService.getAllUrlResource();
        //遍历，如果当前请求的URL地址和菜单中的某一条记录的pattern属性匹配上了，那么就可以获取当前请求所需要的权限
        for (UrlResource urlResource : allUrlResource) {
            if (antPathMatcher.match(urlResource.getPattern(), requestURI)) {
                String[] roles = urlResource.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
        //当所有url不匹配，即数据库中没有配置该条url，则返回null，此时受保护对象能否进行访问取决于AbstractSecurityInterceptor对象中的rejectPublicInvocations属性
        //该属性默认为false，表示当返回null时允许访问受保护对象
        return null;
    }

    /**
     * 该方法用来返回所有的权限属性，以便在项目启动阶段做校验，如不需要校验，则直接返回null即可
     *
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 该方法表示当前对象支持处理的受保护对象时FilterInvocation
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
