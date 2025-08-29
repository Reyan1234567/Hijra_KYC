
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final JwtService jwtUtil;
    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthInput body) {
        String username = body.getUsername();
        String password = body.getPassword();

        if (username == null || password == null) {
            throw new BadCredentialsException("Missing credentials");
        }

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (auth.isAuthenticated()) {
            log.info("User authenticated successfully");
            CustomLdapUserDetails ldapUser = (CustomLdapUserDetails) auth.getPrincipal();
            String userId = ldapUser.getUsername();

            // Check if user exists in DB
            Optional<UserProfile> optional = userProfileRepository.findByUsername(userId);
            UserProfile userProfile;

            if (optional.isPresent()) {
                userProfile = optional.get();

                if (userProfile.getStatus() != 1) {
                    // Status is not active → blocked
                    throw new AuthenticationException("You are currently blocked and can't login");
                }

            } else {
                // ----------------- START: NEW LDAP USER REGISTRATION -----------------
                userProfile = new UserProfile();
                userProfile.setUserName(username);
                userProfile.setFirstName(ldapUser.getFirstName());
                userProfile.setLastName(ldapUser.getLastName());
                userProfile.setGender(ldapUser.getGender());
                userProfile.setPhoneNumber(ldapUser.getPhoneNumber());
                userProfile.setStatus(1); // active
                userProfile.setPhotoUrl(null);

                // Default role id = 1
                Role defaultRole = roleRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("Default role not found"));
                userProfile.setRoleId(defaultRole);

                // ----------------- BRANCH LOGIC -----------------
                Branch branch = null;
                String branchName = ldapUser.getBranchName();

                if (branchName != null && !branchName.isEmpty()) {
                    Optional<Branch> optionalBranch = branchRepository.findByName(branchName);

                    if (optionalBranch.isPresent()) {
                        branch = optionalBranch.get();
                    } else {
                        // Branch not found → create new branch in DB
                        branch = new Branch();
                        branch.setName(branchName);
                        branch.setPhone("0000000000"); // default phone
                        branch.setStatus(1);          // active
                        branch.setDistrictCode(null);   // optional: set a default district or null
                        branch.setDistrictName(null);
                        branch = branchRepository.save(branch); // save and get the ID
                        log.info("Created new branch: {}", branchName);
                    }
                }

                userProfile.setBranch(branch);
                // -------------------------------------------------

                // Save new user
                userProfileRepository.save(userProfile);
                // ----------------- END: NEW LDAP USER REGISTRATION -----------------
            }

            UserPrincipal userPrincipal = new UserPrincipal(userProfile);
            // Generate JWT token
            String accessToken = jwtUtil.generateAccessToken(userPrincipal);
            String refreshToken = jwtUtil.generateRefreshToken(userPrincipal);

            // Prepare user info for response
            UserInfo userInfo = UserInfo.builder()
                    .username(userProfile.getUserName())
                    .role(userProfile.getRoleId().getRoleName())
                    .userId(userProfile.getId())
                    .build();

            // Set authentication in context
            Authentication auth2 = new CustomAuthentication(userProfile);
            SecurityContextHolder.getContext().setAuthentication(auth2);

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, userInfo));
        } else {
            throw new BadCredentialsException("Username or Password incorrect");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshToken){
        log.info("Refreshing JWT token");
        RefreshResponse refresh = service.accessRefreshToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok(refresh);
    }
}
