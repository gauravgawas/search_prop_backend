package com.gaurav.searchProp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.searchProp.model.User;
import com.gaurav.searchProp.repository.UserRepository;
import com.gaurav.searchProp.security.JwtUtil;
import com.gaurav.searchProp.security.SecurityConfig;
import com.gaurav.searchProp.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwt;
    
    public UserController(UserService userService,JwtUtil jwt) {
    	this.userService=userService;
    	this.jwt = jwt;
    }
    
    @PostMapping("/register")
    public Map<String,Object> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user) {
        return userService.loginUser(user);
    }
    
    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return "Missing token";

        String token = authHeader.substring(7);
        if (!jwt.validateToken(token)) return "Invalid token";

        String username = jwt.getUsernameFromToken(token);
        return "Hello, " + username + "! This is your profile.";
    }
	
}
