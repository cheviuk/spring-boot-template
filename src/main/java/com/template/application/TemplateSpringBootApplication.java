package com.template.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.template")
@EnableWebMvc
@EnableJpaRepositories("com.template.repo")
@EntityScan("com.template.entity")
public class TemplateSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateSpringBootApplication.class, args);
    }
}
