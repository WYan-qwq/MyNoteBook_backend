package com.example.mynotebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Paths;

@Configuration
public class WebStaticConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把运行目录下的 uploads 映射到 /files/**
        // Windows/Linux 都可用
        String uploadsPath = Paths.get("uploads").toAbsolutePath().toString() + "/";

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + uploadsPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 如果你的 App/前端域名不同源，可以放开 CORS
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}