package com.wei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class RocketmqConsumer2Application {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqConsumer2Application.class, args);
    }

}
