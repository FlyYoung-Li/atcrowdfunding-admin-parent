package com.crowdfunding.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-22 15:07
 **/
@EnableFeignClients
@SpringBootApplication
public class MainType {
    public static void main(String[] args) {
        SpringApplication.run(MainType.class,args);
    }
}
