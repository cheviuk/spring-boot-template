package com.template.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("com.template")
@EnableWebMvc
public class TemplateSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateSpringBootApplication.class, args);
    }
}
