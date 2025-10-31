package com.gaurav.searchProp.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.gaurav.searchProp.model.User;
import com.gaurav.searchProp.repository.UserRepository;
import com.gaurav.searchProp.security.JwtUtil;
import com.gaurav.searchProp.security.SecurityConfig;


@Service
public class UserService {

    private final UserRepository userRepo;
    private final SecurityConfig securityConfig;
    private final JwtUtil jwt;
    Map<String, Object> res = new HashMap<>();
    public UserService(UserRepository userRepo,SecurityConfig securityConfig,JwtUtil jwt) {
    	this.userRepo=userRepo;
    	this.securityConfig=securityConfig;
		this.jwt = jwt;
    }

    public Map<String,Object> registerUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
        	res.put("Status", "FAIL");
        	res.put("Message", "Email already exists!");
            return res;
        }
        if (userRepo.existsByUsername(user.getUsername())) {
        	res.put("Status", "FAIL");
        	res.put("Message", "Username already exists!");
            return res;
        }
       
        String hashedPassswod=securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassswod);
        userRepo.save(user);
        res.put("Status", "OK");
    	res.put("Message", "User registered successfully!");
        return res;
    }
    public Map<String,Object> loginUser(User user) {
    	List<User> users = userRepo.findByUsername(user.getUsername());
       
        if (users.isEmpty()) {
        	res.put("Status", "FAIL");
         	res.put("Message", "User not found!");
            return res;
        }
        
        User dbres = users.get(0);

        if (securityConfig.passwordEncoder().matches(user.getPassword(),dbres.getPassword())) {
        	res.put("Status", "OK");
         	res.put("Token",  jwt.generateToken(user.getUsername()));
            return res;
        } else {
        	res.put("Status", "FAIL");
         	res.put("Message", "Invalid password!");
            return res;
        }
    }
    
    
}