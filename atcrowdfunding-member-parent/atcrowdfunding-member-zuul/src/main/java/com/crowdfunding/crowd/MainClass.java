package com.crowdfunding.crowd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-20 09:14
 **/
@EnableZuulProxy
@SpringBootApplication
public class MainClass {
    public static void main(String[] args) {

        SpringApplication.run(MainClass.class,args);
    }
}
