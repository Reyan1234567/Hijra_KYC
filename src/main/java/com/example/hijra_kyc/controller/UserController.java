package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.Auth.AuthInput;
import com.example.hijra_kyc.dto.Auth.AuthResponse;
import com.example.hijra_kyc.dto.Auth.RefreshRequest;
import com.example.hijra_kyc.dto.Auth.RefreshResponse;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.User;
import com.example.hijra_kyc.repository.UserRepo;
import com.example.hijra_kyc.service.JwtService;
import com.example.hijra_kyc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepo repo;
    private final JwtService jwtService;

    @PostMapping("/register")
    public User register(@RequestBody AuthInput user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthInput user, HttpServletResponse response) {
        AuthResponse res=service.verify(user, response);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@CookieValue(name="refreshToken") String refreshToken, HttpServletRequest request, HttpServletResponse response){
        log.info("THE REFRESH ROUTE"+refreshToken);
        RefreshResponse refresh=service.accessRefreshToken(request, response, refreshToken);
        return ResponseEntity.ok(refresh);
    }
}
