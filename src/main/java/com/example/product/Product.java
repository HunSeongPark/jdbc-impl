package com.example.product;

import com.example.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private Long id;
    private Order order;
    private String name;

    public Product(Order order, String name) {
        this.order = order;
        this.name = name;
    }
}
