// WebConfig.java
package com.zys.elec.config;

import com.zys.elec.interceptor.JWTInterceptor;

import org.springframework.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/**"); // 拦截所有请求

        // registry.addInterceptor(jwtInterceptor)
        // .addPathPatterns("/**") // 拦截所有请求
        // .excludePathPatterns("/login/**", "/signup/**", "/error/**"); //
        // 凡是以这些开头的请求路径都不会会被拦截
        // System.out.println("拦截器注册成功");
    }
}