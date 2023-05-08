package com.example.jdbc.member;

import com.example.jdbc.order.Order;
import com.example.jdbc.team.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Member {

    private Long id;
    private Team team;
    private String name;
    private int age;
    private List<Order> orderList;

    public Member(Team team, String name, int age) {
        this.team = team;
        this.name = name;
        this.age = age;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }
}
