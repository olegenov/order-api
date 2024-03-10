package com.example.orderapi.service;

import com.example.orderapi.entity.Dish;

import java.util.List;

public interface DishService {
    List<Dish> all();

    Dish get(Long id);

    Dish save(Dish dish);

    Dish update(Dish dish);

    boolean delete(Long id);
}