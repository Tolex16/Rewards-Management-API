package com.balancee.Balancee.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/api/v1/**")
                .allowedOrigins("* ")
                .allowedHeaders("GET", "POST","PUT","DELETE")
                .allowedHeaders("*", "Origin", "Content-Type", "Accept")
                .allowCredentials(true)
                .maxAge(3600);

    }
}
