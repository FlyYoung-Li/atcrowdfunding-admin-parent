package com.crowdfunding.crowd.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-22 10:24
 **/
@RestController
public class RedisRemoteHandler {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    public ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value){

        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();

            operations.set(key,value);
        } catch (Exception exception) {
            exception.printStackTrace();

            return  ResultEntity.failed(exception.getMessage());
        }

        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    public ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time")Long time,
            @RequestParam("timeUnit") TimeUnit timeUnit){

        try {
            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

            stringStringValueOperations.set(key,value,time,timeUnit);
        } catch (Exception exception) {
            // 1. 抛出异常，返回带错误信息的ResultEntity
            exception.printStackTrace();
            ResultEntity.failed(exception.getMessage());
        }
            // 2. 正常执行，返回正确执行的ResultEntity
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/get/redis/value/by/key/remote")
    public ResultEntity<String>getRedisValueByKeyRemote(
            @RequestParam("key") String key){

        String value = null;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            value = operations.get(key);
            if(value == null ){
                return ResultEntity.failed(CrowdConstant.MESSAGE_ERROR_PHONE_NUMBER);
            }
        } catch (Exception exception) {
            // 1. 抛出异常，返回带错误信息的ResultEntity
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
        // 2. 正常执行，返回正确数据的ResultEntity

        return ResultEntity.successWithData(value);
    }

    @RequestMapping("/remove/redis/key/value/remote")
    public ResultEntity<String>removeRedisKeyValueRemote(
            @RequestParam("key") String key) {

        try {
            Boolean delete = redisTemplate.delete(key);
            if(!delete){
               return ResultEntity.failed("删除失败") ;
            }
        } catch (Exception exception) {
            // 1. 抛出异常，返回带错误信息的ResultEntity
            exception.printStackTrace();
            return  ResultEntity.failed(exception.getMessage());
        }
        // 2. 正常执行，返回正确数据的ResultEntity
        return ResultEntity.successWithoutData();
    }


}
