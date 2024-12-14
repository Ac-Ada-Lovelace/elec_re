// JWTInterceptor.java
package com.zys.elec.interceptor;

import com.zys.elec.service.JWTAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTAuthService jwtAuthService;

    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse
    // response, Object handler) throws Exception {

    // String token = request.getHeader("Authorization");

    // if (token != null && token.startsWith("Bearer ")) {
    // token = token.substring(7);
    // if (jwtAuthService.validateToken(token)) {
    // // 验证通过，放行请求
    // return true;
    // }
    // }
    // // 验证失败，返回401未授权
    // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // return false;
    // }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("请求路径: " + requestURI);

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            // 验证失败，返回401未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        token = token.substring(7);

        if (jwtAuthService.validateToken(token)) {
            // 将 token 存储在请求属性中
            request.setAttribute("token", token);
            return true;
        } else {
            // 验证失败，返回401未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

    }
}
