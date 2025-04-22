package edu.gupt.config.properties;

/**
 * 用于配置不需要保护的资源路径
 * Created by macro on 2018/11/5.
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsProperties {
    private List<String> urls = new ArrayList<>();
}