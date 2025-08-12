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
import org.springframework.security.authentication.AuthenticationManager;
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

    public User register(AuthInput input){
        User user = User.builder()
                .username(input.getUsername())
                .password(encoder.encode(input.getPassword()))
                .role("maker")
                .build();
        return repo.save(user);
    }

    public AuthResponse verify(AuthInput user, HttpServletResponse response) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        System.out.println(auth.isAuthenticated()+auth.getPrincipal().toString());
        if (auth.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            Cookie cookie = getCookie(refreshToken);
            log.info("Refresh key: {}", cookie.getName());
            log.info("Refresh Token: {}", cookie.getValue());
            response.addCookie(cookie);

            User user1=repo.findByUsername(user.getUsername());
            UserInfo userInfo=UserInfo.builder()
                    .username(user1.getUsername())
                    .role(user1.getRole())
                    .userId(user1.getId())
                    .build();
            return new AuthResponse(
                    accessToken,
                    userInfo
            );
        }
        throw new RuntimeException("Invalid credentials");
    }

    private Cookie getCookie(String refreshToken){
        Cookie cookie=new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*7);
        cookie.setAttribute("SameSite", "Strict");
        return cookie;
    }

    public RefreshResponse accessRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
//        log.info("Refresh route do get hit");
//        String refreshToken = getRefreshTokenFromCookie(request);
//        log.info(refreshToken);
        UserDetails userDetails = getUserDetailsFromRefreshToken(refreshToken);
        log.info("User Details: {}", userDetails);
        RefreshResponse refresh = returnFinalRefreshResponse(refreshToken, userDetails, response);
        log.info(refresh.getAccessToken());
        return refresh;
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        try{
            log.info(request.toString());
            Cookie[] cookies = request.getCookies();
            log.info("Cookies prolly null "+Arrays.toString(cookies));
            if(cookies==null){
                throw new AuthenticationException("Cookies are null");
            }
            List<Cookie> refreshToken = Arrays.stream(cookies).filter((cookie) -> Objects.equals(cookie.getName(), "refreshToken")).toList();
            return refreshToken.get(0).getValue();
        }
        catch(AuthenticationException e){
            throw e;
        }
        catch(NullPointerException e){
            throw new AuthenticationServiceException("Invalid refresh token");
        }
        catch(Exception e){
            log.error("Error while getting refreshToken from cookie", e);
            throw new AuthenticationServiceException("Could not get refresh token from the Cookie");
        }
    }

    private UserDetails getUserDetailsFromRefreshToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            User user = repo.findByUsername(username);

            if (user == null) {
                throw new AuthenticationException("Username not found");
            }
            return new UserPrincipal(user); // or however you wrap your user}
        }
        catch(AuthenticationException e){
            throw e;
        }
        catch(JwtException e){
            throw new AuthenticationException("Invalid refresh token");
        }
        catch (Exception e) {
            log.error("Error while getting userDetails from RefreshToken", e);
            throw new AuthenticationServiceException("could not get user details from token");
        }
    }

    private RefreshResponse returnFinalRefreshResponse(String refreshToken, UserDetails userDetails, HttpServletResponse response) {
        try {
            if (jwtService.validateToken(refreshToken, userDetails)) {
                String newAccessToken = jwtService.generateAccessToken(userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(userDetails);
                response.addCookie(getCookie(newRefreshToken));
                return RefreshResponse.builder().accessToken(newAccessToken).build();
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
