package com.didi.corwd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdfundingWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String loginUrl = "/do/success/order";
        String loginView = "confire_success";
        registry.addViewController(loginUrl).setViewName(loginView);
    }
}
