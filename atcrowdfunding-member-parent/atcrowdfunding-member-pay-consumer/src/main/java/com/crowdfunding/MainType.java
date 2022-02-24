package com.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-21 16:04
 **/
@EnableFeignClients
@SpringBootApplication
public class MainType {
    public static void main(String[] args) {
        SpringApplication.run(MainType.class,args);

    }
}
