package com.edsonlabs.redislab.module6_lock.api;

import com.edsonlabs.redislab.module6_lock.service.LockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lock")
public class LockController {

    private final LockService service;

    public LockController(LockService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String response = service.doWorkWithLock();

        if (response.contains("already")) {
            return ResponseEntity.status(409).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
