// package com.example.demo.config;

// import com.redis.testcontainers.RedisContainer;
// import org.springframework.core.annotation.Order;
// import org.testcontainers.containers.MySQLContainer;

// import org.springframework.context.annotation.Configuration;
// import org.testcontainers.utility.DockerImageName;

// @Configuration
// public class TestContainerConfig {

//     private static final MySQLContainer<?> MYSQL_CONTAINER =
//             new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
//                     .withDatabaseName("demo")
//                     .withUsername("root")
//                     .withPassword("password")
//                     .withReuse(true); // reuse 要在 ~/.testcontainers.properties 底下建立設定檔案才行，不能只是在 resources 底下

//     private static final RedisContainer REDIS_CONTAINER =
//             new RedisContainer(DockerImageName.parse("redis:7"))
//                     .withExposedPorts(6379) // 相當於 Dockerfile EXPOSE 宣告對外暴露的 port
//                     .withReuse(true);

//     static {
//         MYSQL_CONTAINER.start();
//         REDIS_CONTAINER.start();
//         // mysql
//         System.setProperty("spring.datasource.url", MYSQL_CONTAINER.getJdbcUrl());
//         System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
//         System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());
//         // redis
//         System.setProperty("redis.host", REDIS_CONTAINER.getHost());
//         System.setProperty("redis.port", REDIS_CONTAINER.getMappedPort(6379).toString()); // 取得宿主機對應容器的隨機 port，例如 43359 -> 6379
//     }
// }
