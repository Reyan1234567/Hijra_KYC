package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.Auth.*;
import com.example.hijra_kyc.exception.AuthenticationException;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.security.CustomAuthentication;
import com.example.hijra_kyc.security.CustomLdapUserDetails;
import com.example.hijra_kyc.service.JwtService;
import com.example.hijra_kyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    public AuthController(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthInput body) {
        log.info("DO YOU EVEN GET HIT???");
        log.error("DO YOU EVEN GET HIT???");

        String username = body.getUsername();
        String password = body.getPassword();

        if (username == null || password == null) {
            throw new BadCredentialsException("Missing credentials");
        }
        Authentication auth;
        // Authenticate user via LDAP
        try{
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }
        catch (Exception e){
            throw new AuthenticationException(e.getMessage());
        }

        log.info("THE ERROR ISN'T HAPPENING in THE AUTH MANAGER");
        log.error("THE ERROR ISN'T HAPPENING in THE AUTH MANAGER");

        if (auth.isAuthenticated()) {
            log.info("SO I AM AUTHENTICATED");
            log.error("SO I AM AUTHENTICATED");
            CustomLdapUserDetails ldapUser = (CustomLdapUserDetails) auth.getPrincipal();
            String userId = ldapUser.getUsername();

            // Check if user exists in DB
            Optional<UserProfile> optional = userProfileRepository.findByUsername(userId);
            UserProfile userProfile;

            if (optional.isPresent()) {
                userProfile = optional.get();

                if (userProfile.getStatus() != 1) {
                    // Status is not active → blocked
                    throw new AuthenticationException("Your Are Currently blocked and can't login");
                }

            } else {
                //find if the user exists
                // User does not exist → save LDAP details in DB
                userProfile = new UserProfile();
                userProfile.setUserId(userId);
                userProfile.setUserName(username);
                userProfile.setFirstName(ldapUser.getFirstName());
                userProfile.setLastName(ldapUser.getLastName());
                userProfile.setGender(ldapUser.getGender());
                userProfile.setPhoneNumber(ldapUser.getPhoneNumber());
                userProfile.setStatus(1); // active
                userProfile.setPhotoUrl(null);

                // Default role id = 2
                Role defaultRole = roleRepository.findById(2L)
                        .orElseThrow(() -> new RuntimeException("Default role not found"));
                userProfile.setRoleId(defaultRole);

                // Set branch if exists
                Branch branch = branchRepository.findById(Long.valueOf(ldapUser.getBranchId()))
                        .orElseThrow(() -> new RuntimeException("Branch not found"));
                userProfile.setBranch(branch);

                userProfileRepository.save(userProfile);
            }

            UserPrincipal userPrincipal = new UserPrincipal(userProfile);
            // Generate JWT token using injected jwtUtil
            String accessToken = jwtUtil.generateAccessToken(userPrincipal);
            String refreshToken = jwtUtil.generateRefreshToken(userPrincipal);

            // Return username, token, and roleId
            UserInfo userInfo = UserInfo.builder()
                    .username(userProfile.getUserName())
                    .role(userProfile.getRoleId().getRoleName())
                    .userId(userProfile.getId())
                    .build();
            Authentication auth2=new CustomAuthentication(userProfile);
            SecurityContextHolder.getContext().setAuthentication(auth2);
            return ResponseEntity.ok(new AuthResponse(
                    accessToken,
                    refreshToken,
                    userInfo
            ));
        } else {
            log.info("I AM NOT AUTHENTICATED");
            log.error("I AM NOT AUTHENTICATED");
            throw new BadCredentialsException("Username or Password incorrect");
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshToken){
        RefreshResponse refresh=service.accessRefreshToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok(refresh);
    }
}