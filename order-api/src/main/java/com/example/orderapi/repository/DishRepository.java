package com.example.orderapi.repository;

import com.example.orderapi.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
