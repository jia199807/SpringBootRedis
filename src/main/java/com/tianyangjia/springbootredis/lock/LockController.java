package com.tianyangjia.springbootredis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lock")
public class LockController {

    @Autowired
    private RedisLockService redisLockService;
    /**
     * 尝试获取锁，如果未提供 value，则使用随机生成的 UUID 作为锁的值。
     *
     * @param key   锁的键
     * @param value 锁的唯一值，可选参数
     * @return 获取锁的结果
     */
    @PostMapping("/lock")
    public String lock(@RequestParam String key, @RequestParam(required = false) String value) {
        // 如果未提供 value，则生成一个随机的 UUID
        String uniqueValue = (value != null && !value.isEmpty()) ? value : UUID.randomUUID().toString();

        boolean isLocked = redisLockService.tryLock(key, uniqueValue, 60);
        if (isLocked) {
            return "Lock acquired with value: " + uniqueValue;
        } else {
            return "Failed to acquire lock.";
        }
    }

    @PostMapping("/unlock")
    public String unlock(@RequestParam String key, @RequestParam String value) {
        boolean isUnlocked = redisLockService.unlock(key, value);
        if (isUnlocked) {
            return "Lock released successfully.";
        } else {
            return "Failed to release lock.";
        }
    }
}
