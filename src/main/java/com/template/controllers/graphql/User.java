package com.template.controllers.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long expired;
    private Boolean locked;
    private Long credentialExpired;
    private Boolean enabled;
    private Role role;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(com.template.dto.User user) {
        BeanUtils.copyProperties(user, this, "role");
        this.role = new Role(user.getRole().name());
    }

    com.template.dto.User toDtoUser() {
        com.template.dto.User user = new com.template.dto.User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setExpired(expired);
        user.setLocked(locked);
        user.setCredentialExpired(credentialExpired);
        user.setEnabled(enabled);
        if (role != null) {
            user.setRole(com.template.dto.Role.valueOf(role.getName()));
        }
        return user;
    }
}