package com.edsonlabs.redislab.module4_pubsub.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    // chamado pelo MessageListenerAdapter (método nomeado lá)
    public void handleMessage(String message) {
        log.info("📥 Mensagem recebida pelo listener: {}", message);
    }
}
