package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@RestController
public class TestController {

  @Autowired
  private JdbcClient jdbcClient;

  // @Autowired
  // @Qualifier("redisClient")
  // private RedisClient redis;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private ObjectMapper mapper;

  @GetMapping("/")
  public String test() {
    return "this is a test";
  }

  @GetMapping("/users")
  public List<User> getAllUser() {
    String key = "users:all";
    String value = redisTemplate.opsForValue().get(key);

    if (value != null && !value.isBlank()) {
      return mapper.readValue(value, new TypeReference<>() {
      });
    }

    List<User> users = jdbcClient.sql("SELECT * FROM users")
        .query(User.class)
        .list();
    redisTemplate.opsForValue().set(key, mapper.writeValueAsString(users));
    return users;
  }

  @GetMapping("/users/{userId}")
  public User getUserById(@PathVariable Long userId) {
    String key = "users:id:" + userId;
    String value = redisTemplate.opsForValue().get(key);

    if (value != null && !value.isBlank()) {
      return mapper.readValue(value, User.class);
    }

    User user = jdbcClient.sql("SELECT * FROM users WHERE ID = :userId")
        .param("userId", userId)
        .query(User.class)
        .single();
    redisTemplate.opsForValue().set(key, mapper.writeValueAsString(user));
    return user;
  }

  @PostMapping("/users")
  public String addUser(@RequestBody User user) {
    jdbcClient.sql("INSERT INTO users(name, email, phone, age) VALUES(:name, :email, :phone, :age)")
        .param("name", user.getName())
        .param("email", user.getEmail())
        .param("phone", user.getPhone())
        .param("age", user.getAge())
        .update();
    return "Add User Success";
  }

}
