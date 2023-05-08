package com.example.jdbc.member;

import com.example.jdbc.order.Order;
import com.example.jdbc.order.OrderService;
import com.example.jdbc.order.OrderState;
import com.example.jdbc.team.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }

    public Long save(MemberRequest request, Long teamId) {
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO members(team_id, name, age) " +
                            "values (?, ?, ?)",
                    new String[]{"member_id"}
            );
            pstmt.setLong(1, teamId);
            pstmt.setString(2, request.getName());
            pstmt.setInt(3, request.getAge());
            return pstmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Member> findById(Long memberId) {
        List<Member> results = jdbcTemplate.query(
                "SELECT members.member_id AS memberId, teams.team_id AS teamId, members.name AS memberName, age, teams.name AS teamName " +
                        "FROM members JOIN teams ON members.team_id = teams.team_id " +
                        "LEFT JOIN orders ON members.member_id = orders.member_id " +
                        "WHERE members.member_id = ?",
                rse,
                memberId
        );
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public List<Member> findAll() {
        return jdbcTemplate.query(
                "SELECT members.member_id AS memberId, teams.team_id AS teamId, members.name AS memberName, age, teams.name AS teamName, " +
                        "order_id AS orderId, state AS orderState " +
                        "FROM members JOIN teams ON members.team_id = teams.team_id " +
                        "LEFT JOIN orders ON members.member_id = orders.member_id",
                rse
        );
    }

    public boolean exists(Long memberId) {
        return !jdbcTemplate.query(
                "SELECT 1 FROM members WHERE member_id = ?",
                (rs, rowNum) -> {
                    if (rs.next()) return true;
                    return false;
                },
                memberId
        ).isEmpty();
    }

    // member - team 1:1
    // member - order 1:N
    static ResultSetExtractor<List<Member>> rse = rs -> {
        Map<Long, Member> memberById = new HashMap<>();
        while (rs.next()) {
            long mId = rs.getLong("memberId");
            long teamId = rs.getLong("teamId");
            long orderId = rs.getLong("orderId");
            OrderState orderState = null;
            if (orderId != 0) orderState = OrderState.valueOf(rs.getString("orderState"));
            String memberName = rs.getString("memberName");
            int age = rs.getInt("age");
            String teamName = rs.getString("teamName");
            Team team = new Team(teamId, teamName);
            Member member = memberById.get(mId);
            if (member == null) {
                member = new Member(mId, team, memberName, age);
                memberById.put(member.getId(), member);
            }
            if (orderState != null) member.addOrder(new Order(orderId, null, orderState));
        }
        return new ArrayList<>(memberById.values());
    };
}
