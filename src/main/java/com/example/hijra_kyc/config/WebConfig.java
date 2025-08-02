package com.example.hijra_kyc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/C:/Users/hp/Videos/Hijra_KYC/upload/");

        registry.addResourceHandler("/userProfiles/**")
                .addResourceLocations("file:/C:/Users/hp/Videos/Hijra_KYC/userProfiles/");
    }
}
