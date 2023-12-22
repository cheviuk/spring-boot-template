package com.template.services;

import com.template.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private String password;
    private long expired;
    private boolean locked;
    private long credentialExpired;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserEntity userEntity) {
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
        return new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getExpired(),
                userEntity.getLocked(),
                userEntity.getCredentialExpired(),
                userEntity.getEnabled(),
                authorityList);
    }

    @Override
    public boolean isAccountNonExpired() {
        return Calendar.getInstance().getTimeInMillis() < expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Calendar.getInstance().getTimeInMillis() < credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
