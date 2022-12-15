package com.example.myproj.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationResolver {

    private final JwtToCurrentUserConverter jwtToCurrentUserConverter;

    public AuthenticatedUser getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }

        Jwt principal = (Jwt) authentication.getPrincipal();
        return jwtToCurrentUserConverter.toCurrentUser(principal, authentication.getAuthorities());
    }
}
