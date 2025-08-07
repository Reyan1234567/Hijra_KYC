package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.AuthResponse;
import com.example.hijra_kyc.model.user.UserPrincipal;
import com.example.hijra_kyc.repository.UserRepo;
import com.example.hijra_kyc.service.userService.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.model.user.Users;
import com.example.hijra_kyc.service.userService.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody Users user) {
        return service.verify(user);
    }
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        String username = jwtService.extractUsername(refreshToken);
        Users user = repo.findByUsername(username);

        UserDetails userDetails = new UserPrincipal(user); // or however you wrap your user
        if (jwtService.validateToken(refreshToken, userDetails)) {
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            return new AuthResponse(newAccessToken, newRefreshToken, user.getUsername(), user.getRole());
        }

        throw new RuntimeException("Invalid refresh token");
    }



}
