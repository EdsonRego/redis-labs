package com.edsonlabs.redislab.module6_lock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class LockService {

    private static final Logger log = LoggerFactory.getLogger(LockService.class);

    private final StringRedisTemplate redis;
    private static final String LOCK_KEY = "lab:distributed-lock";
    private static final Duration LOCK_TTL = Duration.ofSeconds(10);

    public LockService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public String doWorkWithLock() {

        // Valor único do lock (importante para segurança do release)
        String lockValue = UUID.randomUUID().toString();

        // Tentando adquirir lock com NX + EX (SET IF NOT EXISTS + TTL)
        Boolean acquired = redis.opsForValue()
                .setIfAbsent(LOCK_KEY, lockValue, LOCK_TTL);

        if (Boolean.FALSE.equals(acquired)) {
            // Outro processo já mantém o lock
            log.warn("❌ Lock já adquirido por outra instância.");
            return "Lock already acquired. Try again later.";
        }

        try {
            log.info("🔐 Lock adquirido com sucesso: {}", lockValue);

            // Simulando algum processamento crítico
            Thread.sleep(3000);

            return "Task executed with lock.";

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Task interrupted.";

        } finally {
            // Antes de remover, verifica se o valor é o mesmo (importante!!)
            String currentValue = redis.opsForValue().get(LOCK_KEY);

            if (lockValue.equals(currentValue)) {
                redis.delete(LOCK_KEY);
                log.info("🔓 Lock liberado: {}", lockValue);
            } else {
                log.warn("⚠ Lock expirado automaticamente antes do release.");
            }
        }
    }
}
