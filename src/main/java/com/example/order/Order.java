package com.example.order;

import com.example.member.Member;
import com.example.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private Long id;
    private Member member;
    private OrderState state;
    private List<Product> productList;

    public Order(Member member) {
        this.member = member;
    }

    public Order(Member member, OrderState state) {
        this.member = member;
        this.state = state;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
