package com.example.hijra_kyc.mapper;

import java.util.Collection;
import java.util.Collections;

import com.example.hijra_kyc.model.User;
import com.example.hijra_kyc.model.UserProfile;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private final UserProfile user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("ROLE: {}", user.getRoleId().getRoleName());
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRoleId().getRoleName()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    public Long getUserId() {
        return user.getId();
    }
}
