package com.example.hijra_kyc.mapper;

import java.util.Collection;
import java.util.Collections;

import com.example.hijra_kyc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails{
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("ROLE: {}",user.getRole());
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getUserId() {
        return user.getId();
    }
}
