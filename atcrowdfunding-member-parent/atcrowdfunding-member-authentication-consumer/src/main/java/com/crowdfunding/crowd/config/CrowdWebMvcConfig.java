package com.crowdfunding.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-24 08:40
 **/
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 1. 前往注册页面的请求
        String  urlPath = "/auth/member/to/register/page";
        // 2. 注册页面
        String viewName = "member-register";


        // 添加注册页面的view-controller
        registry.addViewController(urlPath).setViewName(viewName);
        // 添加登录页面的view-controller
        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        // 添加登陆后前往主页面的view-controller
        registry.addViewController("/auth/member/to/main/page").setViewName("member-center");
        // 前往我的众筹的view-controller
        registry.addViewController("/member/my/crowd").setViewName("member-crowd");
    }
}
