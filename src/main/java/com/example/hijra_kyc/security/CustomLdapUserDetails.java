package com.example.hijra_kyc.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomLdapUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final String emptire; // role number as string

    public CustomLdapUserDetails(String username, String password, String emptire) {
        this.username = username;
        this.password = password;
        this.emptire = emptire;
    }

    public String getEmptire() { return emptire; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return java.util.List.of(); }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

