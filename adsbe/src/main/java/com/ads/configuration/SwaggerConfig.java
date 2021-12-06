package com.ads.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 *  Configuration of swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * After defining Docket bean, the select() method returns an instance of ApiSelector Builder
     * controls the endpoints exposed by swagger
     * @return Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .pathMapping("/");
    }

    /**
     * Contains custom information about the APU
     * @return ApiInfo - info about the project such as name, description, version, terms, contact, licence
     */
    private ApiInfo metaData() {

        Contact contact = new Contact("Group 13", "https://<TBD>/",
                "<TBD - email>");

        return new ApiInfo(
                "Project 3 - ADS",
                "ADD description to swagger TODO",
                "1.0",
                "Terms of Service: Have Fun and Respect the others",
                contact,
                "MIT License",
                "https://opensource.org/licenses/MIT",
                new ArrayList<>());
    }
}
