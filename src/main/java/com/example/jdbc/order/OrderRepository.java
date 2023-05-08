package com.example.jdbc.order;

import com.example.jdbc.member.Member;
import com.example.jdbc.member.MemberRequest;
import com.example.jdbc.team.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
                        "WHERE members.member_id = ?",
                rse,
                memberId
        );
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    public List<Member> findAll() {
        return jdbcTemplate.query(
                "SELECT members.member_id AS memberId, teams.team_id AS teamId, members.name AS memberName, age, teams.name AS teamName " +
                        "FROM members JOIN teams ON members.team_id = teams.team_id",
                rse
        );
    }

    // member - team 1:1
    // member - order 1:N
    static ResultSetExtractor<List<Member>> rse = rs -> {
        Map<Long, Member> memberById = new HashMap<>();
        while (rs.next()) {
            long mId = rs.getLong("memberId");
            long teamId = rs.getLong("teamId");
            String memberName = rs.getString("memberName");
            int age = rs.getInt("age");
            String teamName = rs.getString("teamName");
            Team team = new Team(teamId, teamName);
            memberById.put(mId, new Member(mId, team, memberName, age));
        }
        return new ArrayList<>(memberById.values());
    };
}
