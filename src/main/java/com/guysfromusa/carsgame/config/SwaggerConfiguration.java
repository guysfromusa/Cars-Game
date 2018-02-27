package com.guysfromusa.carsgame.config;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Sebastian Mikucki, 27.02.18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${api.version:1.0}")
    private String apiVersion;

    @Value("${api.title:Rest API}")
    private String apiTitle;

    @Value("${api.description:REST API documentation}")
    private String apiDescription;

    @Value("${api.contact.names:}")
    private String contactNames;

    @Value("${api.contact.mail:}")
    private String contactMail;

    @Value("${api.contact.www:}")
    private String contactWww;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guysfromusa.carsgame"))
                .paths(PathSelectors.regex("/v1.*"))
                .build()
                .apiInfo(info());
    }

    private ApiInfo info() {
        return new ApiInfo(
                apiTitle,
                apiDescription,
                apiVersion,
                "Terms of service",
                new Contact(contactNames, contactWww, contactMail),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                ImmutableList.of());
    }
}
