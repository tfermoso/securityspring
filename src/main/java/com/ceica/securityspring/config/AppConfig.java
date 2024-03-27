package com.ceica.securityspring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${user.image.directory}")
    private String userImageDirectory;

    public String getUserImageDirectory() {
        return userImageDirectory;
    }
}
