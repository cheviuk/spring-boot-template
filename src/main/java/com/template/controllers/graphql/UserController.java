package com.template.controllers.graphql;

import com.template.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@CommonsLog
public class UserController {
    private final UserService userService;

//    @SchemaMapping
//    public Role role(User user) {
//        log.info("Executed: @SchemaMapping Role role(User user) with: user = " + user);
//
//        return new Role(user.getRole().getId());
//    }
//
//    @SchemaMapping
//    public List<User> users(Role role) {
//        log.info("Executed: @SchemaMapping List<User> users(Role role) with: role = " + role);
//
//        return userService.getUsersByRoleName(role.getName())
//                .stream().parallel()
//                .map(User::new).toList();
//    }

    //
    @BatchMapping
    public Mono<Map<User, Role>> roles(List<User> users) {
        log.info("Executed: @BatchMapping Mono<Map<Role, User>> roles(List<User> users) with: users = " + users);

        return Mono.just(users.parallelStream().collect(Collectors.toMap(user -> user, User::getRole)));
    }

    @BatchMapping(field = "users", typeName = "Role")
    public Mono<Map<Role, List<User>>> users(List<Role> roles) {
        log.info("Executed @BatchMapping Mono<Map<User, Role>> users(List<Role> roles) with: roles = " + roles);

        List<com.template.dto.User> dtoUsers = userService
                .getUsersByRoleNames(roles.parallelStream().map(Role::getName).toList());
        List<User> users = dtoUsers.parallelStream().map(User::new).toList();
        Map<Role, List<User>> map = users.parallelStream().collect(Collectors.groupingBy(User::getRole));
        Arrays.stream(com.template.dto.Role.values())
                .map(Role::new)
                .forEach(role -> map.putIfAbsent(role, Collections.emptyList()));
        return Mono.just(map);
    }
    //

    @QueryMapping("roles")
    public List<Role> roles(@Argument("filter") Map<String, List<Object>> filter) {
        log.info(String.format("""
        Executed: @QueryMapping("roles") \
        List<Role> roles(@Argument("filter") Map<String, List<Object>> filter) with: filter = %s""",
                filter));

        List<?> ids = filter.get("ids");
        List<?> names = filter.get("names");

        if (ids == null && names == null
                || (ids != null && ids.size() == 0) && (names != null && names.size() == 0)) {
            return List.of(com.template.dto.Role.values()).parallelStream().map(Role::new).toList();
        }

        var result = new ArrayList<Role>();

        if (ids != null) {
            for (Object id : ids) {
                result.add(new Role(com.template.dto.Role.values()[((Long) id).intValue()]));
            }
        }

        if (names != null) {
            for (Object name : names) {
                result.add(new Role(com.template.dto.Role.valueOf((String) name)));
            }
        }

        return result.stream().distinct().toList();
    }

    @QueryMapping("users")
    public List<User> users(@Argument("filter") Map<String, List<Object>> filter) {
        log.info(String.format("""
        Executed: @QueryMapping("users") \
        List<User> users(@Argument("filter") Map<String, List<Object>> filter) with: filter = %s""",
                filter));

        List<?> ids = filter.get("ids");
        List<?> names = filter.get("names");

        if (ids == null && names == null
                || (ids != null && ids.size() == 0) && (names != null && names.size() == 0)) {
            return userService.getAllUsers().parallelStream().map(User::new).toList();
        }

        List<Long> longIds;
        if (ids == null) {
            longIds = List.of();
        } else {
            longIds = ids.stream().parallel().map(object -> (Long) object).toList();
        }

        List<String> stringNames;
        if (names == null) {
            stringNames = List.of();
        } else {
            stringNames = names.stream().parallel().map(object -> (String) object).toList();
        }

        return userService.getUsersByIdsOrUsernames(longIds, stringNames).stream().parallel().map(User::new).toList();
    }

    @MutationMapping("createUser")
    public User createUser(
            @Argument("username") String username,
            @Argument("password") String password,
            @Argument("email") String email) {
        log.info(String.format("""
                        Executed: @MutationMapping("createUser") \
                        User createUser(@Argument("username") String username,\
                                    @Argument("password") String password,\
                                    @Argument("email") String email) \
                                    with: username = %s, password = %s, email = %s""",
                username, password, email));

        return new User(userService.saveUser(new User(username, password, email).toDtoUser()));
    }

    @MutationMapping("deleteUsers")
    public String deleteUsers(@Argument("filter") Map<String, List<Object>> filter) {
        log.info(String.format("""
                Executed: @MutationMapping("deleteUsers") \
                String deleteUsers(@Argument("filter") Map<String, List<Object>> filter) \
                with: filter = %s""",  filter));

        List<?> ids = filter.get("ids");
        List<?> names = filter.get("names");

        List<Long> longIds;
        if (ids == null) {
            longIds = List.of();
        } else {
            longIds = ids.stream().parallel().map(object -> (Long) object).toList();
        }

        List<String> stringNames;
        if (names == null) {
            stringNames = List.of();
        } else {
            stringNames = names.stream().parallel().map(object -> (String) object).toList();
        }

        return userService.deleteByIdsAndUsernames(longIds, stringNames);
    }
}
