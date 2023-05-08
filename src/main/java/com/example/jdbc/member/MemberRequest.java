package com.example.jdbc.member;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {
    private String name;
    private int age;
    private String teamName;
}
