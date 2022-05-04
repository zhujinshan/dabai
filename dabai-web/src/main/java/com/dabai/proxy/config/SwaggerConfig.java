package com.dabai.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/dabai/doc.html
 * @author: jinshan.zhu
 * @date: 2022/05/04 11:42
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置swagger的bean实例
     * <p>
     * @param environment
     * @return
     */
    @Bean
    public Docket docket(Environment environment) {
        //Profiles直接获取环
        Profiles profiles = Profiles.of("test");
        boolean enable = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                //配置swagger信息
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dabai.proxy.controller"))
                .build();
    }

    /**
     * 配置swagger信息的类，通过DocumentationType.SWAGGER_2.apiInfo调用这个方法
     */
    public ApiInfo apiInfo() {

        Contact contact = new Contact("xxx", "http://dabai.com", "xxx@dabai.com");

        return new ApiInfoBuilder()
                .title("dabai-web") //网站标题
                .description("dabai web RESTful APIs") //网站描述
                .version("v1.0") //版本
                .contact(contact) //联系人
                .build();
    }

}
