package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "role")
@Entity
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private Role() {}

    @Builder
    public Role(String name) {
        this.name = name;
    }
}
