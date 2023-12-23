package com.app.domain.users_management.model;

import com.app.application.dto.token.AuthorizationDto;
import com.app.application.dto.user.GetUserDto;
import com.app.infrastructure.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Builder
public class User {
    @Getter
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private int isActive;

    public User withPassword(String password) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public User withActiveState(int isActive) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role.toString())
                .isActive(isActive)
                .build();
    }

    public boolean isActive() {
        return isActive == 1;
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(id, username, email);
    }

    public AuthorizationDto toAuthorizationDto() {
        return new AuthorizationDto(role);
    }
}
