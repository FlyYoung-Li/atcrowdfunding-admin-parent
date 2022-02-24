package com.crowdfunding.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-20 09:14
 **/
@SpringBootApplication/*(exclude = DataSourceAutoConfiguration.class)*/
@EnableEurekaServer
public class MainClass {
    public static void main(String[] args) {

        SpringApplication.run(MainClass.class,args);
    }
}
