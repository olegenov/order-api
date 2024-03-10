package com.example.orderapi.controller;

import com.example.orderapi.dto.AddDishRequest;
import com.example.orderapi.entity.Dish;
import com.example.orderapi.entity.Order;
import com.example.orderapi.entity.OrderStatus;
import com.example.orderapi.repository.DishRepository;
import com.example.orderapi.repository.OrderRepository;
import com.example.orderapi.service.DishService;
import com.example.orderapi.service.OrderService;
import com.example.orderapi.service.impl.DishServiceImpl;
import com.example.orderapi.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Order> getAllOrders() {
        return service.all();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Order> createOrder(Principal principal) {
        Order savedOrder = service.create(principal.getName());

        return ResponseEntity.ok(savedOrder);
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id, Principal principal) {
        Order order = service.get(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.getStatus() == OrderStatus.CANCELED) {
            return ResponseEntity.badRequest().build();
        }

       boolean cancelled = service.cancelOrder(order, principal.getName());

        if (!cancelled) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Order> payOrder(@PathVariable Long id, Principal principal) {
        Order order = service.get(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        if (order.getStatus() != OrderStatus.READY) {
            return ResponseEntity.badRequest().build();
        }

        boolean payed = service.payOrder(order, principal.getName());

        if (!payed) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(order);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public List<Order> myOrders(Principal principal) {
        return service.getOrdersByUsername(principal.getName());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = service.get(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Order> addDish(@PathVariable Long id, @RequestBody AddDishRequest request) {
        Order order = service.get(id);
        Long dishId = request.getDishId();

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        Order updatedOrder = service.addDish(id, dishId);

        if (updatedOrder == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedOrder);
    }
}
