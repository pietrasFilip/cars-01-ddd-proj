package com.app.application.validator.token.impl;

import com.app.application.dto.token.AuthenticationDto;
import com.app.application.validator.token.AuthenticationDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.application.validator.token.generic.TokenValidator.validateNull;


@Component
public class AuthenticationDtoValidatorImpl implements AuthenticationDtoValidator {
    @Override
    public void validate(AuthenticationDto authenticationDto) {
        validateNull(authenticationDto.username(),"AuthenticationDto - username is null or empty");
        validateNull(authenticationDto.password(), "Password is null or empty");
    }
}
