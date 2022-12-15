package com.example.myproj.service;

import com.example.myproj.config.props.JwtProperties;
import com.example.myproj.enums.ApiResponseCode;
import com.example.myproj.exception.InvalidTokenDataException;
import com.example.myproj.security.AuthenticatedUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class TokenProvider {

    private static final String ACCESS_TOKEN_SUBJECT = "Access_Token";
    private static final String USER_NAME = "username";
    private static final String PHONE = "phone";
    private static final String ROLES = "roles";
    private static final String USER_ID = "userId";
    private static final String SESSION_ID = "sessionId";

    private final JwtProperties jwtProperties;

    @SuppressWarnings("unchecked")
    public AuthenticatedUser getPrincipalFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return new AuthenticatedUser()
                    .setId(((Integer) body.get(USER_ID)).longValue())
                    .setUserName((String) body.get(USER_NAME))
                    .setPhone((String) body.get(PHONE))
                    .setAuthorities(((List<String>) body.get(ROLES)).stream().map(SimpleGrantedAuthority::new).collect(toList()));
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException(ApiResponseCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException(ApiResponseCode.INVALID_TOKEN);
        } catch (SecurityException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException(ApiResponseCode.INVALID_TOKEN_SIGNATURE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException(ApiResponseCode.INVALID_TOKEN_DATA);
        }
    }

    public String buildToken(String sessionId, AuthenticatedUser principal) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(SESSION_ID, sessionId)
                .claim(USER_ID, principal.getId())
                .claim(USER_NAME, principal.getUserName())
                .claim(PHONE, principal.getPhone())
                .claim(ROLES, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()))
                .compact();
    }

}
