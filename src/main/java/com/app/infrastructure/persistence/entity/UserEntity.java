package com.app.infrastructure.persistence.entity;

import com.app.domain.users_management.model.Role;
import com.app.domain.users_management.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private int isActive;

    public User toDomain() {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(Role.valueOf(role))
                .isActive(isActive)
                .build();
    }

    public User toDomainWithPassword(String password) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(Role.valueOf(role))
                .isActive(isActive)
                .build();
    }

    public User toDomainWithActiveState(int isActive) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(Role.valueOf(role))
                .isActive(isActive)
                .build();
    }
}
