package com.example.orderapi.service.impl;

import com.example.orderapi.entity.CustomUser;
import com.example.orderapi.entity.Dish;
import com.example.orderapi.repository.DishRepository;
import com.example.orderapi.repository.UserRepository;
import com.example.orderapi.service.CustomUserService;
import com.example.orderapi.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserServiceImpl implements CustomUserService {
    private final UserRepository userRepository;

    public CustomUserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public List<CustomUser> all() {
        return userRepository.findAll();
    }

    @Override
    public CustomUser get(Long id) {
        if (!userRepository.existsById(id)) {
            return null;
        }

        return userRepository.getReferenceById(id);
    }

    @Override
    public CustomUser save(CustomUser user) {
        return userRepository.save(user);
    }

    @Override
    public CustomUser update(CustomUser user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public CustomUser getByUsername(String username) {
        Optional<CustomUser> userOptional = userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        return userOptional.orElse(null);
    }
}
