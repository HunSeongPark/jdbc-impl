package com.example.jdbc.order;

import com.example.jdbc.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private Long id;
    private Member member;
    private OrderState state;

    public Order(Long id, Member member, OrderState state) {
        this.id = id;
        this.member = member;
        this.state = state;
    }
}
