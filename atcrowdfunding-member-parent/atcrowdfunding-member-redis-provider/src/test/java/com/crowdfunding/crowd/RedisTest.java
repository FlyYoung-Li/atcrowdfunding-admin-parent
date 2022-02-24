package com.crowdfunding.crowd;

import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-22 09:52
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void redisTest(){
        // 1.获取operations对象
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 2. 封装数据
        valueOperations.set("apple","red");
    }
    @Test
    public void redisWithTimeoutTest(){

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("fruit","banana",2000, TimeUnit.SECONDS);
    }
}
