package com.example.jdbc.order;

import com.example.jdbc.member.Member;
import com.example.jdbc.team.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }

    public Long save(Long memberId) {
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO orders(member_id) " +
                            "values (?)",
                    new String[]{"order_id"}
            );
            pstmt.setLong(1, memberId);
            return pstmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(Order order) {
        jdbcTemplate.update(
                "UPDATE orders SET state=? WHERE orders.order_id=?",
                order.getState().toString(),
                order.getId()
        );
    }

    public boolean exists(Long orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT 1 FROM orders WHERE order_id = ?",
                (rs, rowNum) -> {
                    if (rs.next()) return true;
                    return false;
                },
                boolean.class,
                orderId
        );
    }

    public Optional<Order> findById(Long orderId) {
        List<Order> results = jdbcTemplate.query(
                "SELECT * FROM orders WHERE orders.order_id = ?",
                mapper,
                orderId
        );
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public List<Order> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM orders",
                mapper
        );
    }

    static RowMapper<Order> mapper = (rs, rowNum) ->
            new Order(
                    rs.getLong("order_id"),
                    null,
                    OrderState.valueOf(rs.getString("state"))
            );
}
