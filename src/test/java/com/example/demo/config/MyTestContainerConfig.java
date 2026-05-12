package com.example.demo.config;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class MyTestContainerConfig {
    @Bean
    // Springboot 自動裝配會提供預設的與容器連線設定，例如不需要再寫 driver-class-name
    // 且這裡的優先級高於 application.yml
    @ServiceConnection
    public MySQLContainer<?> mySQLContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("demo")
                .withUsername("root")
                .withPassword("password")
                .withUrlParam("serverTimezone", "Asia/Taipei")
                .withUrlParam("characterEncoding", "UTF-8");
    }

    @Bean
//    @ServiceConnection
    public GenericContainer<?> redisContainer() {
        return new GenericContainer<>(DockerImageName.parse("redis:7")).withExposedPorts(6379);
    }

//    @Bean
//    public RedisContainer redisContainer() {
//        return new RedisContainer("redis:7").withExposedPorts(6379);
//    }

    @Bean
    public DynamicPropertyRegistrar redisProperties(GenericContainer<?> redisContainer) {
        return (properties) -> {
            properties.add("redis.host", redisContainer::getHost);
            properties.add("redis.port", redisContainer::getFirstMappedPort);
        };
    }
}
