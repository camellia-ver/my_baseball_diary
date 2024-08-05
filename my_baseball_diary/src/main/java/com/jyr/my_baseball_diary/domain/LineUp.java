package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "line_up")
@Getter
@Entity
public class LineUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "no", nullable = false)
    private Integer no;
}
