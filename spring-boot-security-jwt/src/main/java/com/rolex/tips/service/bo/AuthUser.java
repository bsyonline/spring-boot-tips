/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.service.bo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * @author rolex
 * @since 2021
 */
@Data
public class AuthUser implements UserDetails {
    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public AuthUser() {
    }

    public AuthUser(String username,
                    String password,
                    boolean enabled,
                    boolean accountNonExpired,
                    boolean credentialsNonExpired,
                    boolean accountNonLocked,
                    Set<GrantedAuthority> authorities) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

}
