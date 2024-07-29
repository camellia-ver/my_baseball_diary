package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Table(name = "user_roles")
@Entity
@Getter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long UserId;

    @Column(name = "role_id")
    private Long RoleId;

    @Builder
    public UserRole(Long userId, Long roleId) {
        UserId = userId;
        RoleId = roleId;
    }
}
