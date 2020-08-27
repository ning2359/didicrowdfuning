package com.didi.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String controllerUrl = "/auth/member/to/reg/page";
        String viewUrl = "member_reg";
        registry.addViewController(controllerUrl).setViewName(viewUrl);

        String loginUrl = "/auth/member/to/login/page";
        String loginView = "member_log";
        registry.addViewController(loginUrl).setViewName(loginView);

        String centerUrl = "/auth/do/member/center";
        String centerView = "member_center";
        registry.addViewController(centerUrl).setViewName(centerView);

        String crowdUrl = "/auth/do/member/crowd";
        String crowdView = "member_crowd";
        registry.addViewController(crowdUrl).setViewName(crowdView);
    }
}
