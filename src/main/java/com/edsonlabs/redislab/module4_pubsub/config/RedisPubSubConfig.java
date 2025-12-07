package com.edsonlabs.redislab.module4_pubsub.config;

import com.edsonlabs.redislab.module4_pubsub.listener.MessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("lab-pubsub-channel");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(MessageListener listener) {
        // por padrão, procura handleMessage(String message)
        return new MessageListenerAdapter(listener, "handleMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,
            ChannelTopic topic) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic);
        return container;
    }
}
