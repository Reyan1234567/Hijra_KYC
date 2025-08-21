package com.example.hijra_kyc.Filter;

import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserProfileRepository userProfileRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username  = null;
//      extract username from token
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
//      use extracted username and construct a UserDetails object and validate token and set up a security context
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            Optional<UserProfile> userProfile=userProfileRepository.findByUsername(username);
            if(!userProfile.isPresent()){
                throw new AuthenticationException("username not found");
            }
            UserPrincipal userDetails = new UserPrincipal(userProfile.get());
            log.info("{} {} {}", userDetails.getUserId(), userDetails.getUsername(), userDetails.getAuthorities());
            if(jwtService.validateToken(token, userDetails)){
//      create an authentication object representing the authenticated user
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
