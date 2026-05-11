package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import com.redis.testcontainers.RedisContainer;

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
                    .withPassword("password");
  }

  @Bean
  @ServiceConnection
  public RedisContainer redisContainer() {
    return new RedisContainer(DockerImageName.parse("redis:7"))
                    .withExposedPorts(6379);
  }
}
