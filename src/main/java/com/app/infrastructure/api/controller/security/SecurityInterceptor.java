package com.app.infrastructure.api.controller.security;

import com.app.application.service.token.TokensService;
import com.app.infrastructure.api.controller.uri.SecurityUri;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.app.infrastructure.api.controller.uri.SecurityUri.*;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private final Environment environment;
    private final TokensService tokensService;
    private final Map<SecurityUri, List<String>> uris;
    private static final Logger logger = LogManager.getRootLogger();

    public SecurityInterceptor(Environment environment, TokensService tokensService) {
        this.environment = environment;
        this.tokensService = tokensService;
        this.uris = initUris();
    }

    private Map<SecurityUri, List<String>> initUris() {
        return Map.of(
                ADMIN, Arrays.stream(environment.getRequiredProperty("uri.ADMIN")
                        .split(",")).toList(),
                IS_AUTH, Arrays.stream(environment.getRequiredProperty("uri.IS_AUTH")
                        .split(",")).toList(),
                PERMITTED_ALL, Arrays.stream(environment.getRequiredProperty("uri.PERMITTED_ALL")
                        .split(",")).toList()
        );
    }

    private boolean containsUri(SecurityUri key, String uri) {
        return uris.get(key).stream().anyMatch(uri::startsWith);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var uriFromRequest = request.getRequestURI();

        if (!containsUri(PERMITTED_ALL, uriFromRequest)) {
            var accessCookie = getAccessCookie(request.getCookies());
            var authorizedUser = tokensService.parseTokens(accessCookie);

            if (
                    (containsUri(IS_AUTH, uriFromRequest) && !authorizedUser.isAuth()) ||
                    (containsUri(ADMIN, uriFromRequest) && !authorizedUser.isAdmin())
            ) {
                logger.error("Not authorized user.");
                throw new IllegalStateException("Access Denied!");
            }
        }
        return true;
    }

    private static String getAccessCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("access"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
    }
}
