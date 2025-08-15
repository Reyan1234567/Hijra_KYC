package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.Auth.*;
import com.example.hijra_kyc.exception.AuthenticationException;
import com.example.hijra_kyc.exception.AuthenticationServiceException;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.User;
import com.example.hijra_kyc.repository.UserRepo;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(AuthInput input) {
        User user = User.builder()
                .username(input.getUsername())
                .password(encoder.encode(input.getPassword()))
                .role("maker")
                .build();
        return repo.save(user);
    }

    public AuthResponse verify(AuthInput user) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (auth.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                String accessToken = jwtService.generateAccessToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);

                User user1 = repo.findByUsername(user.getUsername());
                UserInfo userInfo = UserInfo.builder()
                        .username(user1.getUsername())
                        .role(user1.getRole())
                        .userId(user1.getId())
                        .build();
                return new AuthResponse(
                        accessToken,
                        refreshToken,
                        userInfo
                );
            }
        } catch (Exception e) {
            log.error("Invalid username or password", e);
            throw new AuthenticationServiceException(e.getMessage());
        }
        throw new BadCredentialsException("Invalid username or password");
    }


    public RefreshResponse accessRefreshToken(String refreshToken) {
        UserDetails userDetails = getUserDetailsFromRefreshToken(refreshToken);
        RefreshResponse refresh = returnFinalRefreshResponse(refreshToken, userDetails);
        log.info(refresh.getAccessToken());
        return refresh;
    }

    private UserDetails getUserDetailsFromRefreshToken(String token) {
        try {
            log.info("check: getUserDetailsFromRefreshToken");
            String username = jwtService.extractUsername(token);
            User user = repo.findByUsername(username);

            if (user == null) {
                throw new AuthenticationException("Username not found");
            }
            return new UserPrincipal(user); // or however you wrap your user}
        } catch (AuthenticationException e) {
            throw e;
        } catch (JwtException e) {
            throw new AuthenticationException("Invalid refresh token");
        } catch (Exception e) {
            log.error("Error while getting userDetails from RefreshToken", e);
            throw new AuthenticationServiceException("could not get user details from token");
        }
    }

    private RefreshResponse returnFinalRefreshResponse(String refreshToken, UserDetails userDetails) {
        try {
            log.info("check returnFinalRefreshResponse");
            if (jwtService.validateToken(refreshToken, userDetails)) {
                String newAccessToken = jwtService.generateAccessToken(userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(userDetails);
                return RefreshResponse.builder()
                        .refreshToken(newRefreshToken)
                        .accessToken(newAccessToken)
                        .build();
            } else {
                throw new AuthenticationException("Bad credentials");
            }
        } catch (AuthenticationException e) {
            throw e;
        } catch (JwtException e) {
            throw new AuthenticationException("Error related to Jwt");
        } catch (Exception e) {
            log.error("Error while validating refresh token", e);
            throw new AuthenticationServiceException("RefreshResponse server related Error");
        }
    }
}
