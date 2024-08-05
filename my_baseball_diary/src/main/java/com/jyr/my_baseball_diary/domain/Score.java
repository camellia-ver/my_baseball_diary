package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "score")
@Getter
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
}
