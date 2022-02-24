package com.crowdfunding.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-20 09:14
 **/
@MapperScan("com.crowdfunding.crowd.mapper")
@SpringBootApplication/*(exclude= {DataSourceAutoConfiguration.class})*/
public class MainClass {
    public static void main(String[] args) {

        SpringApplication.run(MainClass.class,args);
    }
}
