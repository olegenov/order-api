package com.example.orderapi.service;

import com.example.orderapi.entity.Dish;
import com.example.orderapi.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> all();

    Order get(Long id);

    Order save(Order order);

    Order addDish(Long orderId, Long dishId);

    boolean cancelOrder(Order order, String username);

    List<Order> getOrdersByUsername(String username);

    Order create(String name);

    boolean payOrder(Order order, String name);
}