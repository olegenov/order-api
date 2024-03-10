package com.example.orderapi.controller;

import com.example.orderapi.entity.CustomUser;
import com.example.orderapi.repository.UserRepository;
import com.example.orderapi.service.CustomUserService;
import com.example.orderapi.service.impl.CustomUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signup")
@AllArgsConstructor
public class SignupController {
    private final CustomUserService service;
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CustomUser savedUser = service.save(user);
        return ResponseEntity.ok(savedUser);
    }
}