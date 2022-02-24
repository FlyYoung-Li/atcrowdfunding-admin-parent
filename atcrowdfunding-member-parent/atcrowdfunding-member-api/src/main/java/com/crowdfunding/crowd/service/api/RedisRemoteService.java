package com.crowdfunding.crowd.service.api;

import com.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-22 10:20
 **/
@FeignClient("crowdfunding-redis-provider")
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value/remote")
    public ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value);

    // 这不叫方法的重载，发过来的请求都一样，就是方法名不一样（带超时时间）。
    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    public ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time")Long time,
            @RequestParam("timeUnit")TimeUnit timeUnit);

    @RequestMapping("/get/redis/value/by/key/remote")
    public ResultEntity<String>getRedisValueByKeyRemote(
            @RequestParam("key") String key);

    @RequestMapping("/remove/redis/key/value/remote")
    public ResultEntity<String>removeRedisKeyValueRemote(
            @RequestParam("key") String key);


}
