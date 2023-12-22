package com.template.services;

import com.template.dto.Role;
import com.template.dto.User;
import com.template.entity.UserEntity;
import com.template.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            userEntity.setRole(Role.USER_ROLE);
        } else {
            userEntity.setRole(user.getRole());
        }

        if (user.getEnabled() == null) {
            userEntity.setEnabled(true);
        } else {
            userEntity.setEnabled(user.getEnabled());
        }

        if (user.getLocked() == null) {
            userEntity.setLocked(false);
        } else {
            userEntity.setLocked(user.getLocked());
        }

        if (user.getExpired() == null) {
            userEntity.setExpired(User.UserUtils.getLongMillisecondsInFuture(Calendar.YEAR, 1));
        } else {
            userEntity.setExpired(user.getExpired());
        }

        if (user.getCredentialExpired() == null) {
            userEntity.setCredentialExpired(User.UserUtils.getLongMillisecondsInFuture(Calendar.MONTH, 6));
        } else {
            userEntity.setCredentialExpired(user.getCredentialExpired());
        }

        return new User(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found"));
        return UserDetailsImpl.build(userEntity);
    }

    public List<User> getUsersByRoleName(String name) {
        return userRepository.findAllByRole(Role.valueOf(name))
                .parallelStream()
                .map(User::new).toList();
    }

    public List<User> getUsersByRoleNames(List<String> roleNames) {
        return userRepository.findAllByRoleIn(
                roleNames.parallelStream().map(roleName -> Enum.valueOf(Role.class, roleName)).toList()
                ).parallelStream().map(User::new).toList();
    }

    public List<User> getUsersByIdsOrUsernames(List<Long> ids, List<String> usernames) {
        return userRepository.findByIdInOrUsernameIn(ids, usernames).stream().parallel().map(User::new).toList();
    }

    public List<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), true).map(User::new).toList();
    }

    public String deleteByIdsAndUsernames(List<Long> idList, List<String> usernameList) {
        return userRepository.deleteByIdInOrUsernameIn(
                idList.parallelStream().toArray(Long[]::new),
                usernameList.parallelStream().toArray(String[]::new));
    }
}
