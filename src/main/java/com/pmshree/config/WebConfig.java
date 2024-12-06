package com.pmshree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**") // Path in the URL
                .addResourceLocations("classpath:/static/assets/") // Physical folder
                .setCachePeriod(3600); // Cache for 1 hour (3600 seconds)
    }

}

