package com.edsonlabs.redislab.module3_ratelimit.api;

import com.edsonlabs.redislab.module3_ratelimit.service.RateLimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratelimit")
public class RateLimitController {

    private final RateLimitService service;

    public RateLimitController(RateLimitService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam String user) {

        boolean allowed = service.isAllowed(user);

        if (!allowed) {
            return ResponseEntity.status(429).body("Too Many Requests for user: " + user);
        }

        return ResponseEntity.ok("Request allowed for user: " + user);
    }
}
