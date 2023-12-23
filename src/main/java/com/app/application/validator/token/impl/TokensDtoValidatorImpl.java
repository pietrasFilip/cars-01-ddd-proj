package com.app.application.validator.token.impl;

import com.app.application.dto.token.TokensDto;
import com.app.application.validator.token.TokensDtoValidator;
import org.springframework.stereotype.Component;

import static com.app.application.validator.token.generic.TokenValidator.validateNull;


@Component
public class TokensDtoValidatorImpl implements TokensDtoValidator {
    @Override
    public void validate(TokensDto tokensDto) {
        validateNull(tokensDto.access(), "TokensDto - access is null or empty");
        validateNull(tokensDto.refresh(), "TokensDto - refresh is null or empty");
    }
}
