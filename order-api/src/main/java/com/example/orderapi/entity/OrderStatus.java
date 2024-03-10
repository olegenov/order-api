package com.example.orderapi.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum OrderStatus {
    ACCEPTED("ACCEPTED"),
    PREPARING("PREPARING"),
    READY("READY"),
    CANCELED("CANCELED"),
    PAYED("PAYED");

    private final String value;
}
