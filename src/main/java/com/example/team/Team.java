package com.example.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private Long id;
    private String name;

    public Team(String name) {
        this.name = name;
    }
}
