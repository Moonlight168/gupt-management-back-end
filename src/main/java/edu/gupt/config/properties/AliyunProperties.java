package edu.gupt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunProperties {
    private String endpoint;
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
