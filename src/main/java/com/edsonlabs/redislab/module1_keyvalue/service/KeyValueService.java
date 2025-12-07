package com.edsonlabs.redislab.module1_keyvalue.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class KeyValueService {

    private final StringRedisTemplate redis;

    public KeyValueService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void set(String key, String value, Long ttlSeconds) {
        if (ttlSeconds == null || ttlSeconds <= 0) {
            redis.opsForValue().set(key, value);
        } else {
            redis.opsForValue().set(key, value, Duration.ofSeconds(ttlSeconds));
        }
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(redis.opsForValue().get(key));
    }

    public boolean delete(String key) {
        Boolean result = redis.delete(key);
        return Boolean.TRUE.equals(result);
    }

    public boolean exists(String key) {
        Boolean result = redis.hasKey(key);
        return Boolean.TRUE.equals(result);
    }

    public Long ttl(String key) {
        return redis.getExpire(key); // -1 sem TTL, -2 não existe
    }
}
