package com.app.application.service.token;


import com.app.application.dto.token.AuthorizationDto;
import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;

public interface TokensService {
    TokensDto generateTokens(Long userId);
    AuthorizationDto parseTokens(String token);
    TokensDto refreshTokens(RefreshTokenDto refreshTokenDto);
}
