package com.example.myproj.security;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class AuthenticatedUser implements Serializable {

    private Long id;
    private String userName;
    private String phone;
    private Collection<? extends GrantedAuthority> authorities;

    public boolean hasRole(Role role) {
        return authorities.stream().anyMatch(grantedAuthority -> role.name().equals(grantedAuthority.getAuthority()));
    }

    public enum Role {
        ROLE_ADMIN, ROLE_PARTNER
    }
}
