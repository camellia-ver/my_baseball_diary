package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @Builder
    public LineUp(Integer no, String position, LocalDate date, String note) {
        this.no = no;
        this.position = position;
        this.date = date;
        this.note = note;
    }
}
