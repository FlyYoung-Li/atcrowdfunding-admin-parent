package com.crowd.mvc.security;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-27 14:55
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private CrowdUserDetailsServices crowdUserDetailsServices;
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //配置请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); 调用父类的方法，就是拦截所有的资源
//        注释掉父类方法，默认资源都是放行的，但是返回的json数据没有放行，角色维护和菜单维护，就是403拒接访问。
//        自己要进行设置，除了登录页面和静态资源放行外，其他的anyRequest，都需要认证。
        http
                .authorizeRequests()   //下面都是，放行静态资源
                .antMatchers("/bootstrap/**")
                .permitAll()
                .antMatchers("/crowd/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                .antMatchers("/webjars/**")
                .permitAll()
                .antMatchers("/admin/to/login/page.html")
                .permitAll()
                .antMatchers("/admin/get.html")
                .access("hasRole('经理')or hasAnyAuthority('user:get')")
             /*   .antMatchers("/role/get/page/info.json")
                .hasRole("部长")*/

                .anyRequest()
                .authenticated()

                .and()//这里禁用了csrf，不然每次表单都要带csrftoken参数
                .csrf()
                .disable()
                //使用SpringSecurity来做登录
                .formLogin()                        //开启表单登录
                .loginPage("/admin/to/login/page.html")//指定前往登录页面的请求
                .loginProcessingUrl("/security/do/login.html")//指定表单提交的请求
                .usernameParameter("loginAcct")//设置账号参数
                .passwordParameter("userPswd")
                .defaultSuccessUrl("/admin/to/main/page.html")//登录成功后，前往admin主页面的请求

                .and()
                .logout()
                .logoutUrl("/security/do/logout/page.html")
                .logoutSuccessUrl("/admin/to/login/page.html")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_REQUEST_FORBIDDEN_FOR_NO_AUTHORITY));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/pages/system-error.jsp") .forward(httpServletRequest,httpServletResponse);
                    }
                });

    }
    //配置账号

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        super.configure(auth);
       /* builder
                .inMemoryAuthentication()
                .withUser("lxy")
                .password("lxy")
                .roles("ADMIN");*/
       builder
               .userDetailsService(crowdUserDetailsServices)
               .passwordEncoder(getBCryptPasswordEncoder());
    }
}
