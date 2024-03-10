package com.example.orderapi.controller;

import com.example.orderapi.entity.CustomUser;
import com.example.orderapi.repository.UserRepository;
import com.example.orderapi.service.CustomUserService;
import com.example.orderapi.service.impl.CustomUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final CustomUserService service;
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CustomUser> getAllUsers() {
        return service.all();
    }

    @PostMapping
    public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CustomUser savedUser = service.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomUser> getUser(@PathVariable Long id) {
        CustomUser user = service.get(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("User with ID " + id + " successfully deleted");
    }
}
