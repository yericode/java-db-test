package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;

@RestController
public class TestController {

  @Autowired
  private JdbcClient jdbcClient;

  @GetMapping("/")
  public String test() {
    return "this is a test";
  }

  @GetMapping("/users")
  public List<User> getAllUser() {
    return jdbcClient.sql("SELECT * FROM USERS")
        .query(User.class)
        .list();
  }

  @GetMapping("/users/{userId}")
  public User getUserById(@PathVariable Long userId) {
    return jdbcClient.sql("SELECT * FROM USERS WHERE ID = :userId")
        .param("userId", userId)
        .query(User.class)
        .single();
  }

  @PostMapping("/users")
  public String addUser(@RequestBody User user) {
    jdbcClient.sql("INSERT INTO USERS(NAME, EMAIL, PHONE, AGE) VALUES(:name, :email, :phone, :age)")
      .param("name", user.getName())
      .param("email", user.getEmail())
      .param("phone", user.getPhone())
      .param("age", user.getAge())
      .update();
      return "Add User Success";
  }

}
