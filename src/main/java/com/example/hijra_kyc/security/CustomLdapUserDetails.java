package com.example.hijra_kyc.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomLdapUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String phoneNumber;
    private final String branchName;


    public CustomLdapUserDetails(
            String username,
            String password,
            String firstName,
            String lastName,
            String gender,
            String phoneNumber,
            String branchName

    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.branchName= branchName;

    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return java.util.List.of(); }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
