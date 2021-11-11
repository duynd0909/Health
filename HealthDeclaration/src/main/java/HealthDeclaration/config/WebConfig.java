//package HealthDeclaration.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.method.HandlerTypePredicate;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("POST", "PUT", "DELETE", "GET", "PATCH", "OPTIONS")
//                .allowedOrigins("*").allowedHeaders("*").maxAge(3800).allowCredentials(true);
//    }
//
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
////        configurer.addPathPrefix("/api/v1.0", HandlerTypePredicate.forAnnotation(RestController.class));
//    }
//}