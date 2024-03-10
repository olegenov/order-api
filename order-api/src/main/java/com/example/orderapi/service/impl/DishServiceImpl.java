package com.example.orderapi.service.impl;

import com.example.orderapi.entity.Dish;
import com.example.orderapi.repository.DishRepository;
import com.example.orderapi.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> all() {
        return dishRepository.findAll();
    }

    @Override
    public Dish get(Long id) {
        if (!dishRepository.existsById(id)) {
            return null;
        }

        return dishRepository.getReferenceById(id);
    }

    @Override
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public boolean delete(Long id) {
        if (!dishRepository.existsById(id)) {
            return false;
        }

        dishRepository.deleteById(id);
        return true;
    }
}
