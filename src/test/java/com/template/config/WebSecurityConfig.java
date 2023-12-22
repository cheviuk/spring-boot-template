package com.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
// public class WebSecurityConfig {

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll()).build();
//    }
//
//    @Bean
//    UserDetailsManager users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$CpcK7KZlZFDMNdkl/Lb.qu9zLVmpVg6VUWTcLtMOKsyYbY2WxLvje")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$CpcK7KZlZFDMNdkl/Lb.qu9zLVmpVg6VUWTcLtMOKsyYbY2WxLvje")
//                .roles("USER", "ADMIN")
//                .build();
//        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }
//}
