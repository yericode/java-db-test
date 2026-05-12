package com.example.demo.controller;

import com.example.demo.entity.User;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class MyControllerTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                    .withDatabaseName("DEMO")
                    .withUsername("root")
                    .withPassword("password");

    @Container
    static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:7"));

    // @DynamicPropertySource 是給測試方法的靜態屬性用的，想要重用要抽 Base 類別給所有測試類繼承
    // @DynamicPropertyRegistrar 是給測試開發使用的，本質是一個 Bean 可以透過依賴注入來使用
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("redis.host", redisContainer::getHost);
        registry.add("redis.port", redisContainer::getFirstMappedPort);
    }

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    public void someTest() {
        var users = jdbcClient.sql("SELECT * FROM USERS").query(User.class).list();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
