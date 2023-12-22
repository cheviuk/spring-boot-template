package com.template.controllers.graphql;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
class Role {
    Integer id;
    String name;

    public Role(Integer id) {
        this.id = id;
        this.name = com.template.dto.Role.values()[id].name();
    }

    public Role(String name) {
        this.name = name;
        this.id = com.template.dto.Role.valueOf(name).ordinal();
    }

    public Role(com.template.dto.Role role) {
        this(role.ordinal());
    }
}
