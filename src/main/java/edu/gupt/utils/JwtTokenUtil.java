package edu.gupt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.gupt.config.properties.JwtProperties;
import edu.gupt.domain.details.StudentDetails;
import edu.gupt.domain.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private JwtProperties jwtProperties;

    // 生成 JWT Token
    public String generateToken(List<? extends GrantedAuthority> authorities,Long id) {
        // 将角色信息转换为字符串列表
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return JWT.create()
                .withClaim("roles",roles)
                .withClaim("id",id)
                .withIssuedAt(new Date())
                //设置过期时间为一天
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256(jwtProperties.getStudentSecretKey()));
    }

    //验证token是否合法
    public static Boolean validateToken(String token,String key) {
        try {
            JWT.require(Algorithm.HMAC256(key)).build().verify(token);
            //比较id并返回结果
            return true;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            return false;
        }
    }

    public static Authentication parseToken(String token) {
        // 解码 token
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim roles = decodedJWT.getClaim("roles");
        // 检查 roles 是否为 null，如果是则返回一个空列表
        List<String> roleList = roles.isNull() ? Collections.emptyList() : roles.asList(String.class);
        // 获取角色信息并转换为 GrantedAuthority 列表
        List<SimpleGrantedAuthority> authorities = roleList
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Long id = decodedJWT.getClaim("id").asLong();
        Student student = Student.builder()
                .id(id)
                .role(roleList.get(0))
                .build();
        // 创建 StudentDetails 对象，包含用户信息和角色
        StudentDetails studentDetails = StudentDetails.builder()
                .student(student)
                .build();

        // 返回 Authentication 对象
        return new UsernamePasswordAuthenticationToken(studentDetails, null, authorities);
    }
}