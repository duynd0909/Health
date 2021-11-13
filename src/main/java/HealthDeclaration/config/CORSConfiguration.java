package HealthDeclaration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfiguration {
    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://front-end-health.herokuapp.com", "https://back-end-health.herokuapp.com", "http://healthdeclare.hipe.com.vn:8080")
                        .allowedMethods("HEAD","GET","POST","PUT","DELETE","PATCH").allowedHeaders("*");
            }
        };
    }
}
