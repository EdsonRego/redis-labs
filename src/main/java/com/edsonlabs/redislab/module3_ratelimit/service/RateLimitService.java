package com.edsonlabs.redislab.module3_ratelimit.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private final StringRedisTemplate redis;

    private static final int MAX_REQUESTS_PER_MINUTE = 5;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    public RateLimitService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean isAllowed(String user) {

        String key = "ratelimit:" + user;

        // incrementa o contador
        Long count = redis.opsForValue().increment(key);

        if (count == 1) {
            // primeira requisição → configurar TTL
            redis.expire(key, WINDOW);
        }

        // se ultrapassou o limite
        return count <= MAX_REQUESTS_PER_MINUTE;
    }
}
