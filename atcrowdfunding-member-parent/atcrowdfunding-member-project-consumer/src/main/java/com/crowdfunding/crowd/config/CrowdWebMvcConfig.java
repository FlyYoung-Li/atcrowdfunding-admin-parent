package com.crowdfunding.crowd.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 14:40
 **/
@Component
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 前往project的协议同意页面
        registry.addViewController("/agree/protocol/page").setViewName("project-protocol");
        // 前往项目发起页面（1）
        registry.addViewController("/project/launch/page").setViewName("project-launch");
        // 前往项目回报页面（2）
        registry.addViewController("/project/return/page").setViewName("project-return");
        // 前往项目确认页面（3）
        registry.addViewController("/project/confirm/page").setViewName("project-confirm");
        // 项目成功创建页面（4）
        registry.addViewController("project/success/page").setViewName("project-success");
    }
}
