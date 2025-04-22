package edu.gupt.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import edu.gupt.config.properties.JwtProperties;
import edu.gupt.utils.JwtTokenUtil;
import edu.gupt.utils.SecurityContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 忽略特定路径
        String path = request.getRequestURI();
        if ("/student/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取token jwtProperties.getStudentToken()的名称需要跟前端传过来的名称一致
        String token = request.getHeader(jwtProperties.getStudentToken());
        if (StringUtils.isNotEmpty(token)) {
            if (JwtTokenUtil.validateToken(token, jwtProperties.getStudentSecretKey())) {
                //解析token
                SecurityContextUtil.storeAuthentication(JwtTokenUtil.parseToken(token));
            }
        }
        filterChain.doFilter(request, response);
    }
}