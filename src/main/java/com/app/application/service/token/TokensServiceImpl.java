package com.app.application.service.token;

import com.app.application.dto.token.AuthorizationDto;
import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;
import com.app.application.validator.token.AuthorizationDtoValidator;
import com.app.application.validator.token.RefreshTokenDtoValidator;
import com.app.application.validator.token.TokensDtoValidator;
import com.app.domain.users_management.model.User;
import com.app.domain.users_management.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor
public class TokensServiceImpl implements TokensService{
    private final UserRepository userRepository;
    private final SecretKey secretKey;
    private final AuthorizationDtoValidator authorizationDtoValidator;
    private final RefreshTokenDtoValidator refreshTokenDtoValidator;
    private final TokensDtoValidator tokensDtoValidator;
    private static final Logger logger = LogManager.getRootLogger();
    private final String accessTokenExpirationTimeMs;
    private final String refreshTokenExpirationTimeMs;
    private final String accessTokenExpirationTimePropertyInRefresh;

    @Override
    public TokensDto generateTokens(Long userId) {
        var currentDate = Date.from(ZonedDateTime.now().toInstant());
        var refreshTokenExpirationDate = Date.from(currentDate.toInstant()
                .plusMillis(Long.parseLong(refreshTokenExpirationTimeMs)));

        return accessAndRefreshToken(userId, refreshTokenExpirationDate);
    }

    @Override
    public AuthorizationDto parseTokens(String token) {
        if (token == null) {
            logger.error("Parse tokens - token is null");
            throw new IllegalStateException("Access Denied!");
        }

        if (isTokenNotValid(token)) {
            logger.error("Parse token - token has been expired");
            throw new IllegalStateException("Authorization error");
        }

        var userId = id(token);
        var authorizedUser =  userRepository
                .findById(userId)
                .map(User::toAuthorizationDto)
                .orElseThrow(() -> new IllegalStateException("Authorization failed"));

        authorizationDtoValidator.validate(authorizedUser);

        return authorizedUser;
    }

    @Override
    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        refreshTokenDtoValidator.validate(refreshTokenDto);

        var token = refreshTokenDto.token();

        if (isTokenNotValid(token)) {
            logger.error("Refresh tokens - refresh token has been expired");
            throw new IllegalStateException("Token error");
        }

        var accessTokenExpirationTimeMsRefresh = accessTokenExpirationDateMsInRefreshToken(token);
        if (accessTokenExpirationTimeMsRefresh < System.currentTimeMillis()) {
            logger.error("Access token has been expired");
            throw new IllegalStateException("Token error");
        }

        var userId = id(token);
        var refreshTokenExpirationDate = expirationDate(token);

        return accessAndRefreshToken(userId, refreshTokenExpirationDate);
    }

    // ---------------------------------------------------------------------------------------------------------
    // Methods for parsing tokens
    // ---------------------------------------------------------------------------------------------------------

    private TokensDto accessAndRefreshToken(Long userId, Date refreshTokenExpirationDate) {
        var currentDate = Date.from(ZonedDateTime.now().toInstant());
        var accessTokenExpirationDate = Date.from(currentDate.toInstant()
                .plusMillis(Long.parseLong(accessTokenExpirationTimeMs)));

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(accessTokenExpirationTimePropertyInRefresh,
                        accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        var refreshedToken = new TokensDto(accessToken, refreshToken);
        tokensDtoValidator.validate(refreshedToken);

        return refreshedToken;
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Long id(String token) {
        return Long.parseLong(claims(token).getSubject());
    }

    private Date expirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenNotValid(String token) {
        return !expirationDate(token).after(Date.from(ZonedDateTime.now().toInstant()));
    }

    private Long accessTokenExpirationDateMsInRefreshToken(String token) {
        return claims(token).get(accessTokenExpirationTimePropertyInRefresh,
                Long.class);
    }
}
