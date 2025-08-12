package com.example.hijra_kyc.Filter;

import com.example.hijra_kyc.service.JwtService;
import com.example.hijra_kyc.service.UsersDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsersDetailsService usersDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username  = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            System.out.println("so it has a bearer");
            token = authHeader.substring(7);
            System.out.println(token);
            username = jwtService.extractUsername(token);
            System.out.println(username);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("so it has a username");
            UserDetails userDetails = usersDetailsService.loadUserByUsername(username);
            System.out.println(userDetails);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
