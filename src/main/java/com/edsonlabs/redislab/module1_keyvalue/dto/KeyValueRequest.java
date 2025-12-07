package com.edsonlabs.redislab.module1_keyvalue.dto;

public record KeyValueRequest(
        String key,
        String value,
        Long ttlSeconds
) {}
