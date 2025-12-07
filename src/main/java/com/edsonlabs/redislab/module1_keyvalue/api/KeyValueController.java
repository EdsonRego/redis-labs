package com.edsonlabs.redislab.module1_keyvalue.api;

import com.edsonlabs.redislab.module1_keyvalue.dto.KeyValueRequest;
import com.edsonlabs.redislab.module1_keyvalue.service.KeyValueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kv")
public class KeyValueController {

    private final KeyValueService service;

    public KeyValueController(KeyValueService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> set(@RequestBody KeyValueRequest request) {
        service.set(request.key(), request.value(), request.ttlSeconds());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        return service.get(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@PathVariable String key) {
        boolean deleted = service.delete(key);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{key}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable String key) {
        return ResponseEntity.ok(service.exists(key));
    }

    @GetMapping("/{key}/ttl")
    public ResponseEntity<Long> ttl(@PathVariable String key) {
        return ResponseEntity.ok(service.ttl(key));
    }
}
