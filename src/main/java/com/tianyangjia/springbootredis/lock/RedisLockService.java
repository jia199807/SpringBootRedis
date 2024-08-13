package com.tianyangjia.springbootredis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLockService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 尝试获取锁
     *
     * @param key        锁的键
     * @param value      锁的唯一值，用于标识持有者
     * @param expireTime 锁的过期时间（秒）
     * @return 如果获取成功返回true，否则返回false
     */
    public boolean tryLock(String key, String value, long expireTime) {
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
        return success != null && success;
    }

    /**
     * 释放锁
     *
     * @param key   锁的键
     * @param value 锁的唯一值，用于验证持有者
     * @return 如果释放成功返回true，否则返回false
     */
    public boolean unlock(String key, String value) {
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            return stringRedisTemplate.delete(key);
        }
        return false;
    }
}
