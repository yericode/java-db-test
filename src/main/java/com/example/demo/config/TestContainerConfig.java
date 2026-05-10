package com.example.demo.config;

import org.testcontainers.containers.MySQLContainer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContainerConfig {

    private static final MySQLContainer<?> MYSQL_CONTAINER =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("demo")
                    .withUsername("root")
                    .withPassword("password")
                    .withReuse(true); // reuse 要在 ~/.testcontainers.properties 底下建立設定檔案才行，不能只是在 resources 底下

    static {
        MYSQL_CONTAINER.start();

        System.setProperty(
                "spring.datasource.url",
                MYSQL_CONTAINER.getJdbcUrl()
        );

        System.setProperty(
                "spring.datasource.username",
                MYSQL_CONTAINER.getUsername()
        );

        System.setProperty(
                "spring.datasource.password",
                MYSQL_CONTAINER.getPassword()
        );
    }
}
