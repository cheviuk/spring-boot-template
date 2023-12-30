package com.template.controllers.rest;

import com.template.dto.User;
import com.template.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("getAllUsers")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
