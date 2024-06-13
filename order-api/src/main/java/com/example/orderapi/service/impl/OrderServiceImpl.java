package com.example.orderapi.service.impl;

import com.example.orderapi.entity.CustomUser;
import com.example.orderapi.entity.Dish;
import com.example.orderapi.entity.Order;
import com.example.orderapi.entity.OrderStatus;
import com.example.orderapi.repository.OrderRepository;
import com.example.orderapi.service.CustomUserService;
import com.example.orderapi.service.DishService;
import com.example.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishService dishService;
    private final CustomUserService userService;

    private final ExecutorService cookService = Executors.newFixedThreadPool(10);

    @Override
    public List<Order> all() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(Long id) {
        if (!orderRepository.existsById(id)) {
            return null;
        }

        return orderRepository.getReferenceById(id);
    }

    public void cook(Order order) {
        cookService.submit(() -> simulateCooking(order));
    }

    public Runnable simulateCooking(Order order) {
        order.setStatus(OrderStatus.PREPARING);
        orderRepository.save(order);

        for (Dish dish : order.getDishes()) {
            try {
                Thread.sleep(dish.getDifficulty() * 60 * 100);
            } catch (InterruptedException ignored) { }
        }

        order.setStatus(OrderStatus.READY);
        orderRepository.save(order);

        return null;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order addDish(Long orderId, Long dishId) {
        Dish dish = dishService.get(dishId);

        if (dish == null) {
            return null;
        }

        Order order = get(orderId);

        if (order == null) {
            return null;
        }

        if (order.getStatus() == OrderStatus.PAYED) {
            return null;
        }

        if (dish.getAmount() == 0) {
            return null;
        }

        dish.setAmount(dish.getAmount() - 1);
        order.addDish(dish);
        cook(order);

        return orderRepository.save(order);
    }

    @Override
    public boolean cancelOrder(Order order, String username) {
        CustomUser user = userService.getByUsername(username);

        if (user.getId() != order.getUserId()) {
            return false;
        }

        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);
        return true;
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        CustomUser user = userService.getByUsername(username);

        return orderRepository.findAll()
                .stream()
                .filter(order -> Objects.equals(order.getUserId(), user.getId()))
                .toList();
    }

    @Override
    public Order create(String username) {
        CustomUser user = userService.getByUsername(username);

        Order order = new Order(user.getId());
        order.setStatus(OrderStatus.ACCEPTED);

        return orderRepository.save(order);
    }

    @Override
    public boolean payOrder(Order order, String username) {
        CustomUser user = userService.getByUsername(username);

        if (user.getId() != order.getUserId()) {
            return false;
        }

        order.setStatus(OrderStatus.PAYED);

        orderRepository.save(order);
        return true;
    }
}
