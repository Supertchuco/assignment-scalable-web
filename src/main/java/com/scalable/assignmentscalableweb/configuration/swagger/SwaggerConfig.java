package com.scalable.assignmentscalableweb.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Docket configuration.
     *
     * @return Docket object
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).useDefaultResponseMessages(false).select()
            .apis(RequestHandlerSelectors.basePackage("com.scalable.assignmentscalableweb.controle"))
            .paths(PathSelectors.any()).build();
    }

    /**
     * API information configuration.
     *
     * @return ApiInfo object
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Diff API")
            .description(
                "This documentation is meant to provide an overview of all available Diff exposed API's.")
            .version("v1").build();
    }

    /**
     * UI configuration.
     *
     * @return UiConfiguration object
     */
    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(null, "list", "alpha", "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
            false, true, null);
    }

}
