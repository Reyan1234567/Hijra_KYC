package com.example.hijra_kyc.controller;



import com.example.hijra_kyc.security.CustomLdapUserDetails;
import com.example.hijra_kyc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) throw new BadCredentialsException("Missing credentials");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Object principal = auth.getPrincipal();
        String emptire = "0";
        if (principal instanceof CustomLdapUserDetails) {
            emptire = ((CustomLdapUserDetails) principal).getEmptire();
        }
        String token = jwtUtil.generateToken(username, emptire);
        return Map.of("token", token, "emptire", emptire);
    }
}

