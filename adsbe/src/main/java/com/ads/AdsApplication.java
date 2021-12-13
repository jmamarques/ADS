package com.ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * class that starts the whole spring application
 */
@SpringBootApplication
public class AdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsApplication.class, args);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver
                = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100 * 1024 * 1024);
        return multipartResolver;
    }
}
