package edu.gupt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gupt.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminToken;

    /**
     * 学生端生成jwt令牌相关配置
     */
    private String studentSecretKey;
    private long studentTtl;
    private String studentToken;

    /**
     * 教师端生成jwt令牌相关配置
     */
    private String teacherSecretKey;
    private long teacherTtl;
    private String teacherToken;

}
