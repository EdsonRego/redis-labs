package com.edsonlabs.redislab.module5_streams.consumer;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StreamConsumer {

    private static final Logger log = LoggerFactory.getLogger(StreamConsumer.class);

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String STREAM_NAME = "lab-stream";
    private static final String GROUP = "lab-group";
    private static final String CONSUMER = "consumer-1";

    public StreamConsumer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            redisTemplate.opsForStream().createGroup(STREAM_NAME, GROUP);
            log.info("Consumer group criado: {}", GROUP);
        } catch (Exception e) {
            log.info("Consumer group {} já existe", GROUP);
        }
    }

    // Executa a cada 3 segundos
    @Scheduled(fixedDelay = 3000)
    public void consumeMessages() {

        Consumer consumer = Consumer.from(GROUP, CONSUMER);

        StreamReadOptions options = StreamReadOptions.empty().count(10).block(2000);

        List<MapRecord<String, Object, Object>> messages =
                redisTemplate.opsForStream().read(consumer, options, StreamOffset.create(STREAM_NAME, ReadOffset.lastConsumed()));

        if (messages != null) {
            for (MapRecord<String, Object, Object> msg : messages) {

                log.info("📩 Mensagem consumida: {}", msg.getValue());

                // Marca como processado
                redisTemplate.opsForStream().acknowledge(STREAM_NAME, GROUP, msg.getId());
            }
        }
    }
}
