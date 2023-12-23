package com.app.application.validator.token.impl;

import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.validator.token.RefreshTokenDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.application.validator.token.generic.TokenValidator.validateNull;


@Component
public class RefreshTokenDtoValidatorImpl implements RefreshTokenDtoValidator {
    @Override
    public void validate(RefreshTokenDto refreshTokenDto) {
        validateNull(refreshTokenDto.token(), "RefreshTokenDto - token is null or empty");
    }
}
