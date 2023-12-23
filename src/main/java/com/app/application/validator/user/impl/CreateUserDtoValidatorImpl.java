package com.app.application.validator.user.impl;

import com.app.application.dto.user.CreateUserDto;
import com.app.application.validator.user.CreateUserDtoValidator;

import static com.app.application.validator.user.generic.UserDtoValidator.validateNull;

public class CreateUserDtoValidatorImpl implements CreateUserDtoValidator {
    @Override
    public void validate(CreateUserDto createUserDto) {
        validateNull(createUserDto.username(), "Username is null or empty");
        validateNull(createUserDto.email(), "Email is null or empty");
        validateNull(createUserDto.password(), "Password is null or empty");
        validateNull(createUserDto.passwordConfirmation(), "Password confirmation is null or empty");
    }
}
