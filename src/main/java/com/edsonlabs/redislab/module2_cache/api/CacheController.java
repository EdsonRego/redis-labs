package com.edsonlabs.redislab.module2_cache.api;

import com.edsonlabs.redislab.module2_cache.service.SlowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final SlowService slowService;

    public CacheController(SlowService slowService) {
        this.slowService = slowService;
    }

    @GetMapping("/expensive")
    public String expensive(@RequestParam String input) {
        return slowService.expensiveOperation(input);
    }
}
