package com.github.rxyor.carp.delayjob.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *<p>
 *SwaggerConfiguration
 *</p>
 *
 * @author liuyang
 * @date 2018-12-07 Fri 13:27:46
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @RequestMapping("/doc/api")
    public String forwardSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket apiDocket() {
        //选择swagger版本
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("对外接口")
            .select()
            .paths(PathSelectors.any())
            .build();
    }

}
