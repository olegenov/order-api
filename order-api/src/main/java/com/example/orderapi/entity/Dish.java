package com.example.orderapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "dishes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "amount")
    @Setter
    private int amount;

    @Column(name = "price")
    @Setter
    private int price;

    @Column(name = "difficulty")
    @Setter
    private int difficulty;
}
