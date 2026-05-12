package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.config.MyTestContainerConfig;

@SpringBootTest
public class TestApplication {
  public static void main(String[] args) {
   SpringApplication.from(Application::main).with(MyTestContainerConfig.class).run(args);
  }
  
}
