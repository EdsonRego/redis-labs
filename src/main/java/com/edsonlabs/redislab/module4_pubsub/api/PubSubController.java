package com.edsonlabs.redislab.module4_pubsub.api;

import com.edsonlabs.redislab.module4_pubsub.publisher.MessagePublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pubsub")
public class PubSubController {

    private final MessagePublisher publisher;

    public PubSubController(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody String message) {
        publisher.publish(message);
        return "Mensagem publicada: " + message;
    }
}
