package com.example.jdbc.order;

import com.example.jdbc.member.Member;
import com.example.jdbc.product.Product;
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

    public Order(Long id, Member member, OrderState state, List<Product> productList) {
        this.member = member;
        this.state = state;
        this.productList = productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
