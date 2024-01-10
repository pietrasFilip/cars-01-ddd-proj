package com.app.infrastructure.api.controller.security;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.token.AuthenticationDto;
import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;
import com.app.application.service.token.TokensService;
import com.app.application.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class SecurityController {
    private final TokensService tokensService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseDto<TokensDto> login(@RequestBody AuthenticationDto authenticationDto, HttpServletResponse response) {
        var userId = userService.isUserCorrect(authenticationDto);
        var tokens = tokensService.generateTokens(userId);

        var accessTokenCookie = new Cookie("access", tokens.access());
        var refreshTokenCookie = new Cookie("refresh", tokens.refresh());

        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return new ResponseDto<>(tokens);
    }

    @PostMapping("/refresh")
    public ResponseDto<TokensDto> refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return new ResponseDto<>(tokensService.refreshTokens(refreshTokenDto));
    }

}
