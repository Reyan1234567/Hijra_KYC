package com.example.hijra_kyc.service;

import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.User;
import com.example.hijra_kyc.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {
    private final UserRepo repo;
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repo.findByUsername(username);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
