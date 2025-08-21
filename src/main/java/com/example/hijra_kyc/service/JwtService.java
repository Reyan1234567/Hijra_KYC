package com.example.hijra_kyc.service;

import com.example.hijra_kyc.exception.AuthenticationException;
import com.example.hijra_kyc.exception.AuthenticationServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${refresh.pass}")
    private String refreshPass;

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, 1000 * 10); // 15 minutes
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, 1000 * 60 * 60 * 24 * 7); // 7 days
    }

    private String buildToken(UserDetails userDetails, long expirationMillis) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", userDetails.getAuthorities());
            return Jwts.builder()
                    .claims(claims)
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                    .signWith(getKey())
                    .compact();
        } catch (NullPointerException e) {
            log.error("Error building token", e);
            throw new AuthenticationException("Error building token");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private SecretKey getKey(){
        try {
            byte[] keyBytes = Decoders.BASE64.decode(refreshPass);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (AuthenticationException e) {
            throw e;
        }
        catch(JwtException e){
            throw new AuthenticationException("Jwt Exception "+e);
        }
        catch (Exception e) {
            log.error("Error getting secret key", e);
            throw new BadCredentialsException("Error getting secret key");
        }
    }

    public String extractUsername(String token){
        log.info("check: extractUsername");
        log.info("THE TOKEN {}",token);
        String claim = extractClaim(token, Claims::getSubject);
        log.info("THE SUBJECT {}",claim);
        return claim;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        log.info("THE Claims {}",claims);
        System.out.println(claims);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try{
            log.info("check: extractAllClaims");
            log.info("TOKEN {}",token);
            if (token == null) {
                throw new AuthenticationException("token is null");
            }
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch(JwtException e){
            log.error("Jwt Exception {}", String.valueOf(e));
            throw new AuthenticationException("Jwt Exception "+e);
        }
        catch(AuthenticationException e){
            log.error("Jwt Exception {}", String.valueOf(e));
            throw e;
        }
        catch(Exception e){
            log.error("Jwt Exception {}", String.valueOf(e));
            log.error("Error extracting claims", e);
            throw new AuthenticationServiceException("Error getting claims");
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("CHECK: validateToken");
        log.info("THE TOKEN {}",token);
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
