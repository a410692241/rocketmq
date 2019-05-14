package com.wei.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rocketmq.provider")
@Configuration
@ToString
public class ProducerConfig {
    private String namesrvAddr;
    
    private String groupName;
}