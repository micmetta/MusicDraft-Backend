//package com.microservice.authentication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api") // Imposta il percorso dell'API
//                .allowedOrigins("http://localhost:4200") // L'origine del tuo frontend Angular
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true);
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8082") // Consentire solo questo origin
//                .allowedMethods("GET", "POST") // Consentire i metodi GET e POST
//                .allowCredentials(true); // Consentire le credenziali
//    }

//}
