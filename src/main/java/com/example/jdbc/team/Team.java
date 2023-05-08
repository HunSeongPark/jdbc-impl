package com.example.jdbc.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private Long id;
    private String name;

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
