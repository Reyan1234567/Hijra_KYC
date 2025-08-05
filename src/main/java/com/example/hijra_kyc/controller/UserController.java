package com.example.hijra_kyc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.model.user.Users;
import com.example.hijra_kyc.service.userService.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }
    
    
}
