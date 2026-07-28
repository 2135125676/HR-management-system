package com.group2.cms.config;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;

/**
 * swagger配置类
 * @author tangjy
 */
@Configuration
public class SwaggerConfig {
    public OpenAPI springShopOpenAPI() {

        Contact contact = new Contact();
        contact.setName("tangjy");
        contact.setEmail("tangjy@qq.com");
        contact.setUrl("www.tangjy.com");

        return new OpenAPI()
                .info(new Info()
                        .title("校园社团活动管理系统")
                        .contact(contact)
                        .description("这个是一个校园社团活动管理系统")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.tangjy.com")));
    }
}
