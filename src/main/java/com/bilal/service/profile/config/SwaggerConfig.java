package com.bilal.service.profile.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    //TODO: These values should be loaded from a properties file.
    private String title = "User Profile Api";
    private String description = "All purpose User Profile Api";
    private String termsOfServiceUrl = "";
    private Contact contact = new Contact("Muhammad Bilal Fazal", "", "mbilalf@gmail.com");
    private String license = "Apache 2.0";
    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";

    @Bean
    public Docket profileApi() {

        ApiInfo apiInfo = new ApiInfo(title, description, "1.0", termsOfServiceUrl, contact, license, licenseUrl, Collections.emptyList());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bilal.service.profile"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiInfo);
    }
}
