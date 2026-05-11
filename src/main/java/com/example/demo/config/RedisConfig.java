package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.RedisClient;

@Configuration
@Order(2)
public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Bean
    public ConnectionPoolConfig poolConfig() {
        ConnectionPoolConfig poolConfig = new ConnectionPoolConfig();
        poolConfig.setMaxTotal(50);    // 最大連線數
        poolConfig.setMaxIdle(10);     // 最大空閒連線
        poolConfig.setMinIdle(5);      // 最小空閒連線
        return poolConfig;
    }

    @Bean("redisClient")
    public RedisClient redisClient() {
        return RedisClient.builder()
                .hostAndPort(host, Integer.parseInt(port))
                .poolConfig(poolConfig())
                .build();
    }
}
