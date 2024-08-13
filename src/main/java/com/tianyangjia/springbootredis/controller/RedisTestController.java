package com.tianyangjia.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis-test")
    public String testRedis() {
        // 设置值
        redisTemplate.opsForValue().set("myKey", "Hello Redis!");

        // 获取值
        String value = (String) redisTemplate.opsForValue().get("myKey");

        return "Value from Redis: " + value;
    }
}
