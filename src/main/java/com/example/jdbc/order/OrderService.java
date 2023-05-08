package com.example.jdbc.order;

import com.example.jdbc.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createOrder(Long memberId) {
        if (!memberRepository.exists(memberId)) {
            throw new RuntimeException("member not found");
        }
        return orderRepository.save(memberId);
    }

    @Transactional
    public void updateOrder(Long orderId, OrderState state) {
        orderRepository.update(new Order(orderId, null, state));
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
