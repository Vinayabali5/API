package uk.ac.reigate.config.swagger;

import static springfox.documentation.builders.PathSelectors.regex

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
//@ComponentScan(basePackages = ["uk.ac.reigate.api"])
//@EnableWebMvc
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Import(SwaggerUiConfiguration.class)
public class SwaggerConfig {
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("College Information Database API", "The API documentation for the College Information Database", "1.0.0", "", "", "", "");
        return apiInfo;
    }
    
    @Bean
    public Docket customImplementation() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(MetaClass.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.any())
                .paths(PathSelectors.ant('/api/**'))
                .build();
        return docket;
    }
    
}