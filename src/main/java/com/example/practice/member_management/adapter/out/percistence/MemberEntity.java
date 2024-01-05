package com.example.practice.member_management.adapter.out.percistence;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@RequiredArgsConstructor
//@NoArgsConstructor()
public class MemberEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    private int age;

    @Builder
    public MemberEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
