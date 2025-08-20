//package com.example.hijra_kyc.controller;
//
//import com.example.hijra_kyc.model.Branch;
//import com.example.hijra_kyc.model.Role;
//import com.example.hijra_kyc.model.UserProfile;
//import com.example.hijra_kyc.repository.BranchRepository;
//import com.example.hijra_kyc.repository.RoleRepository;
//import com.example.hijra_kyc.repository.UserProfileRepository;
//import com.example.hijra_kyc.security.CustomLdapUserDetails;
//import com.example.hijra_kyc.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserProfileRepository userProfileRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BranchRepository branchRepository;
//
//    @Autowired
//    public AuthController(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody Map<String, String> body) {
//        String username = body.get("username");
//        String password = body.get("password");
//
//        if (username == null || password == null)
//            throw new BadCredentialsException("Missing credentials");
//
//        // Authenticate user via LDAP
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        CustomLdapUserDetails ldapUser = (CustomLdapUserDetails) auth.getPrincipal();
//        String userId = ldapUser.getUserId();
//
//        // Check if user exists in DB
//        Optional<UserProfile> optional = userProfileRepository.findByUserId(userId);
//        UserProfile userProfile;
//
//        if (optional.isPresent()) {
//            userProfile = optional.get();
//
//            if (userProfile.getStatus() != 1) {
//                // Status is not active → blocked
//                return Map.of("message", "Access denied, user is blocked");
//            }
//
//        } else {
//            // User does not exist → save LDAP details in DB
//            userProfile = new UserProfile();
//            userProfile.setUserId(userId);
//            userProfile.setUserName(username);
//            userProfile.setFirstName(ldapUser.getFirstName());
//            userProfile.setLastName(ldapUser.getLastName());
//            userProfile.setGender(ldapUser.getGender());
//            userProfile.setPhoneNumber(ldapUser.getPhoneNumber());
//            userProfile.setStatus(1); // active
//            userProfile.setPhoto(null);
//
//            // Default role id = 3
//            Role defaultRole = roleRepository.findById(3L)
//                    .orElseThrow(() -> new RuntimeException("Default role not found"));
//            userProfile.setRoleId(defaultRole);
//
//            // Set branch if exists
//            Branch branch = branchRepository.findById(Long.valueOf(ldapUser.getBranchId()))
//                    .orElseThrow(() -> new RuntimeException("Branch not found"));
//            userProfile.setBranch(branch);
//
//            userProfileRepository.save(userProfile);
//        }
//
//        // Generate JWT token
//        String token = JwtUtil.generateTokenFromLdap(ldapUser);
//
//        // Return only username, token, and roleId
//        return Map.of(
//                "username", userProfile.getUserName(),
//                "token", token,
//                "roleId", userProfile.getRoleId().getRoleId()
//        );
//    }
//}
package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.security.CustomLdapUserDetails;
import com.example.hijra_kyc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    private JwtUtil jwtUtil; // inject JwtUtil

    @Autowired
    public AuthController(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null)
            throw new BadCredentialsException("Missing credentials");

        // Authenticate user via LDAP
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        CustomLdapUserDetails ldapUser = (CustomLdapUserDetails) auth.getPrincipal();
        String userId = ldapUser.getUserId();

        // Check if user exists in DB
        Optional<UserProfile> optional = userProfileRepository.findByUserId(userId);
        UserProfile userProfile;

        if (optional.isPresent()) {
            userProfile = optional.get();

            if (userProfile.getStatus() != 1) {
                // Status is not active → blocked
                return Map.of("message", "Access denied, user is blocked");
            }

        } else {
            // User does not exist → save LDAP details in DB
            userProfile = new UserProfile();
            userProfile.setUserId(userId);
            userProfile.setUserName(username);
            userProfile.setFirstName(ldapUser.getFirstName());
            userProfile.setLastName(ldapUser.getLastName());
            userProfile.setGender(ldapUser.getGender());
            userProfile.setPhoneNumber(ldapUser.getPhoneNumber());
            userProfile.setStatus(1); // active
            userProfile.setPhoto(null);

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

        // Generate JWT token using injected jwtUtil
        String token = jwtUtil.generateTokenFromLdap(ldapUser);

        // Return username, token, and roleId
        return Map.of(
                "username", userProfile.getUserName(),
                "token", token,
                "roleId", userProfile.getRoleId().getRoleId()

        );
    }
}
