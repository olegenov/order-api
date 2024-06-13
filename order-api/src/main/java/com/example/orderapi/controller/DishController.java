package com.example.orderapi.controller;

import com.example.orderapi.entity.Dish;
import com.example.orderapi.repository.DishRepository;
import com.example.orderapi.service.DishService;
import com.example.orderapi.service.impl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService service;

    @Autowired
    public DishController(DishRepository dishRepository) {
        this.service = new DishServiceImpl(dishRepository);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<Dish> getAllDishes() {
        return service.all();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        Dish savedDish = service.save(dish);

        return ResponseEntity.ok(savedDish);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Dish> getDish(@PathVariable Long id) {
        Dish dish = service.get(id);

        if (dish == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dish);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Dish with ID " + id + " successfully deleted");
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Dish> changeDish(@PathVariable Long id, @RequestBody Dish updatedDishData) {
        Dish dish = service.get(id);

        if (dish == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedDishData.getName() != null) {
            dish.setName(updatedDishData.getName());
        }

        if (updatedDishData.getAmount() != 0) {
            dish.setAmount(updatedDishData.getAmount());
        }

        if (updatedDishData.getPrice() != 0) {
            dish.setPrice(updatedDishData.getPrice());
        }

        if (updatedDishData.getDifficulty() != 0) {
            dish.setDifficulty(updatedDishData.getDifficulty());
        }

        Dish updatedDish = service.update(dish);

        return ResponseEntity.ok(updatedDish);
    }
}
