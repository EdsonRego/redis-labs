package com.edsonlabs.redislab.module5_streams.producer;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StreamProducer {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String STREAM_NAME = "lab-stream";

    public StreamProducer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(String message) {

        ObjectRecord<String, String> record =
                StreamRecords.newRecord()
                        .ofObject(message)
                        .withStreamKey(STREAM_NAME);

        redisTemplate.opsForStream().add(record);
    }
}
