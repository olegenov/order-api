package com.example.orderapi.service;

import com.example.orderapi.entity.CustomUser;

import java.util.List;

public interface CustomUserService{
    List<CustomUser> all();

    CustomUser get(Long id);

    CustomUser save(CustomUser customUser);

    CustomUser update(CustomUser customUser);

    boolean delete(Long id);

   CustomUser getByUsername(String username);
}