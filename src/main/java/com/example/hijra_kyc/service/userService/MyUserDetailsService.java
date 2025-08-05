package com.example.hijra_kyc.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.model.user.UserPrincipal;
import com.example.hijra_kyc.model.user.Users;
import com.example.hijra_kyc.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if(user == null){
            System.out.println("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        } 
        return new UserPrincipal(user);
    }
    
}
