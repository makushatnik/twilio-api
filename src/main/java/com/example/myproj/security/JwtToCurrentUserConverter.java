package com.example.myproj.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtToCurrentUserConverter {

    private static final String PHONE = "phone";
    private static final String SUB_KEY = "sub";
    private static final String PREFERRED_USERNAME_KEY = "preferred_username";

    public AuthenticatedUser toCurrentUser(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        return new AuthenticatedUser()
                .setId(Long.parseLong(jwt.getClaim(SUB_KEY)))
                .setUserName(jwt.getClaim(PREFERRED_USERNAME_KEY))
                .setPhone(jwt.getClaim(PHONE))
                .setAuthorities(authorities);
    }
}
