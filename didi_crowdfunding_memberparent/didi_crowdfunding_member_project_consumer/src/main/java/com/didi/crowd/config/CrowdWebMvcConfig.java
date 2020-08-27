package com.didi.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/agree/protocol/page").setViewName("project_agree");
        registry.addViewController("/launch/project/page").setViewName("project_launch");
        registry.addViewController("/return/info/page").setViewName("project_return");
        registry.addViewController("/create/confirm/page").setViewName("project_confirm");
        registry.addViewController("/create/success").setViewName("project_success");
        registry.addViewController("/show/detail").setViewName("project_detail");
    }
}
