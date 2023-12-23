package com.app.application.dto.token;


import com.app.domain.users_management.model.Role;

import static com.app.domain.users_management.model.Role.ADMIN;
import static com.app.domain.users_management.model.Role.USER;

public record AuthorizationDto(Role role) {

    public boolean isAuth() {
        return role != null;
    }

    public boolean isUser() {
        return role.equals(USER);
    }

    public boolean isAdmin() {
        return role.equals(ADMIN);
    }
}
