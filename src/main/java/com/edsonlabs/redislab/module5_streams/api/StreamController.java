package com.edsonlabs.redislab.module5_streams.api;

import com.edsonlabs.redislab.module5_streams.producer.StreamProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/streams")
public class StreamController {

    private final StreamProducer producer;

    public StreamController(StreamProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestBody String message) {
        producer.sendMessage(message);
        return "Mensagem enviada ao stream: " + message;
    }
}
