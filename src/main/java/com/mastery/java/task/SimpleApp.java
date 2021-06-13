package com.mastery.java.task;

import com.mastery.java.task.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = JacksonAutoConfiguration.class)
public class SimpleApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class, args);
    }

}
