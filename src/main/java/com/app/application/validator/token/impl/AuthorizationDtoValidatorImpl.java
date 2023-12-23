package com.app.application.validator.token.impl;

import com.app.application.dto.token.AuthorizationDto;
import com.app.application.validator.token.AuthorizationDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.application.validator.token.generic.TokenValidator.validateNull;


@Component
public class AuthorizationDtoValidatorImpl implements AuthorizationDtoValidator {
    @Override
    public void validate(AuthorizationDto authorizationDto) {
        validateNull(authorizationDto.role(),"AuthenticationDto - username is null or empty");
    }
}
