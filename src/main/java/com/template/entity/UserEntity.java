package com.template.entity;

import com.template.dto.Role;
import com.template.dto.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;

@Entity
@Order
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    @Size(min = 3, message = "Йой Not less than 3 symbols")
    private String username;

    @NotNull
    @Size(min = 3, message = "Йой Not less than 3 symbols")
    private String password;

    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NotNull
    private Long expired;

    @NotNull
    private Boolean locked;

    @NotNull
    private Long credentialExpired;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Role role = Role.USER_ROLE;

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
