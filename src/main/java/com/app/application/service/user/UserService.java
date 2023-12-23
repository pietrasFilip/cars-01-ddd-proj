package com.app.application.service.user;


import com.app.application.dto.token.AuthenticationDto;
import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.GetUserDto;

public interface UserService {
    GetUserDto register(CreateUserDto createUserDto);
    GetUserDto activate(Long userId, Long expirationTime);
    GetUserDto findById(Long id);
    GetUserDto findByUsername(String username);
    GetUserDto findByEmail(String email);
    Long isUserCorrect(AuthenticationDto authenticationDto);
}
