package com.example.jdbc.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestParam Long memberId) {
        return ResponseEntity.ok(orderService.createOrder(memberId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> update(@PathVariable Long orderId, @RequestParam OrderState orderState) {
        orderService.updateOrder(orderId, orderState);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
