package com.edsonlabs.redislab.module2_cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SlowService {

    @Cacheable("expensive-operation")
    public String expensiveOperation(String input) {

        simulateSlowOperation();

        return "Resultado processado para: " + input;
    }

    private void simulateSlowOperation() {
        try {
            Thread.sleep(3000); // simula algo pesado (3 segundos)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
