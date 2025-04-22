package edu.gupt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gupt.db")
@Data
public class DbServerProperties {
    private String url;
    private String username;
    private String password;
}
