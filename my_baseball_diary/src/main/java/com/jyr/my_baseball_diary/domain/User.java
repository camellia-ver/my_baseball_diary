package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Table(name = "members")
@Getter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "favorite_team_id")
    private Long favoriteTeamId;

    @CreatedDate
    @Column(name = "create_dt", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "role", nullable = false)
    private String role;

    private User() {}

    @Builder
    public User(String email, String password, String userName,
                Long favoriteTeamId, LocalDateTime createDate, String role) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.favoriteTeamId = favoriteTeamId;
        this.createDate = createDate;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
