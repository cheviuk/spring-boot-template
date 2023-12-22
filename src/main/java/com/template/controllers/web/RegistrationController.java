package com.template.controllers.web;

import com.template.dto.User;
import com.template.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("registration")
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userFormObject", new UserFormObject());
        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("userFormObject") @Valid UserFormObject userFormObject,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User user = new User();
        user
                .setUsername(userFormObject.getUsername())
                .setEmail(userFormObject.getEmail())
                .setPassword(userFormObject.getPassword());
        userService.saveUser(user);
        return "redirect:login";
    }

    @Data
    private static class UserFormObject {
        @Size(min = 3, max = 255)
        private String username;
        @Size(min = 4, max = 255)
        private String password;
        @Email
        @NotEmpty
        private String email;
    }
}
