package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.Auth.*;
import com.example.hijra_kyc.exception.AuthenticationException;
import com.example.hijra_kyc.exception.AuthenticationServiceException;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.User;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.UserProfileRepository;
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

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository repo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserProfileRepository userProfileRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public User register(AuthInput input) {
//        User user = User.builder()
//                .username(input.getUsername())
//                .password(encoder.encode(input.getPassword()))
//                .role("maker")
//                .build();
//        return repo.save(user);
//    }

    public RefreshResponse accessRefreshToken(String refreshToken) {
        UserDetails userDetails = getUserDetailsFromRefreshToken(refreshToken);
        RefreshResponse refresh = returnFinalRefreshResponse(refreshToken, userDetails);
        log.info("THE REFRESH TOKEN");
        log.info(refresh.getAccessToken());
        return refresh;
    }

    private UserDetails getUserDetailsFromRefreshToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
            if (userProfile.isPresent()) {
                return new UserPrincipal(userProfile.get()); // or however you wrap your user}
            }
            throw new AuthenticationException("Can't find user with username "+username);
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
