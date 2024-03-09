package com.monitor.peeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return  new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }
    private ApiInfo apiInfo(){
        Contact contact = new Contact(
                "学不懂Java的小李", // 作者姓名
                "https://blog.csdn.net/xhmico?type=blog", // 作者网址
                "777777777@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("窥视者-接口文档") // 标题
                .description("当你凝视深渊时,深渊也在凝视你") // 描述
                .termsOfServiceUrl("https://www.baidu.com") // 跳转连接
                .version("0.0.1") // 版本
                .license("Swagger-的使用(详细教程)")
                .licenseUrl("https://blog.csdn.net/xhmico/article/details/125353535")
                .contact(contact)
                .build();

    }

}
