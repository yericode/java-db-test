package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.config.MyTestContainerConfig;
import com.example.demo.entity.User;

@SpringBootTest
@Testcontainers
@Import(MyTestContainerConfig.class)
public class MyControllerTest {
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
