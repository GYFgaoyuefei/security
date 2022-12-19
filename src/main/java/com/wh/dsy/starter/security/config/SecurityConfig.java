package com.wh.dsy.starter.security.config;

import com.wh.dsy.starter.security.config.filters.MyJwtAuthenticationFilter;
import com.wh.dsy.starter.security.config.filters.MyLoginFilter;
import com.wh.dsy.starter.security.config.handler.MyAuthenticationFailureHandler;
import com.wh.dsy.starter.security.config.handler.MyAuthenticationSuccessHandler;
import com.wh.dsy.starter.security.config.handler.MyLogoutSuccessHandler;
import com.wh.dsy.starter.security.config.handler.exception.AccessDeniedExceptionHandler;
import com.wh.dsy.starter.security.config.handler.exception.AuthenticationExceptionHandler;
import com.wh.dsy.starter.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * SecurityConfig
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MyJwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    CustomSecurityMetadataSource customSecurityMetadataSource;

    @Autowired
    MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    @Autowired
    AuthenticationExceptionHandler authenticationExceptionHandler;

    @Autowired
    MyLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        String encodingId = "dsy_password_encoder";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(31));
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    MyLoginFilter loginFilter() throws Exception {
        MyLoginFilter loginFilter = new MyLoginFilter();
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return loginFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        //基于url地址的权限管理
        UrlAuthorizationConfigurer<HttpSecurity> urlAuthorizationConfigurer = new UrlAuthorizationConfigurer<>(applicationContext);
        //对象后置处理器ObjectPostProcessor，即一个对象创建成功后，如果还有一些额外的事情需要补充，那么可以通过ObjectPostProcessor来处理
        //在对象后置处理器中将SecurityMetadataSource换成我们自定义的customSecurityMetadataSource
        ObjectPostProcessor<FilterSecurityInterceptor> objectPostProcessor = new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(customSecurityMetadataSource);
                //该属性默认为false，表示当customSecurityMetadataSource中的getAttributes方法返回null时允许访问受保护对象，此处配置为true为不允许访问
//                object.setRejectPublicInvocations(false);
                return object;
            }
        };
        http.apply(urlAuthorizationConfigurer)
                .withObjectPostProcessor(objectPostProcessor);
        //异常处理
        http.exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler)
                .accessDeniedHandler(accessDeniedExceptionHandler);
        http.formLogin()
                .and()
                .csrf().disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(corsConfigurationSource());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        //注销登录
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessHandler(logoutSuccessHandler);
    }


    /**
     * 跨域配置
     *
     * @return
     */
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(10L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
