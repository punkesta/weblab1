package com.noname.weblabs.security.systemuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails {
    
    @Serial
    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    private boolean isBanned;

    public CustomUserDetails(
        int userId,
        String username,
        String password,
        Collection<? extends GrantedAuthority> roles,
        boolean isBanned
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isBanned = isBanned;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {      
        return roles;
    }
}