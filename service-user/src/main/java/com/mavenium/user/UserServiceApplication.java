package com.mavenium.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户微服务启动类
 *
 * @author Mavenium
 */
@SpringBootApplication(scanBasePackages = "com.mavenium")
@EnableDiscoveryClient
@MapperScan("com.mavenium.user.mapper")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
