package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Table(name = "score")
@Getter
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "game_result", updatable = false)
    private String gameResult;

    @Column(name = "team_name", updatable = false)
    private String teamName;

    @Column(name = "one")
    private Integer one;

    @Column(name = "two")
    private Integer two;

    @Column(name = "three")
    private Integer three;

    @Column(name = "four")
    private Integer four;

    @Column(name = "five")
    private Integer five;

    @Column(name = "six")
    private Integer six;

    @Column(name = "seven")
    private Integer seven;

    @Column(name = "eight")
    private Integer eight;

    @Column(name = "nine")
    private Integer nine;

    @Column(name = "ten")
    private Integer ten;

    @Column(name = "eleven")
    private Integer eleven;

    @Column(name = "twelve")
    private Integer twelve;

    @Column(name = "r")
    private Integer r;

    @Column(name = "h")
    private Integer h;

    @Column(name = "e")
    private Integer e;

    @Column(name = "b")
    private Integer b;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @Builder
    public Score(String gameResult, String teamName,
                 Integer one, Integer two, Integer three, Integer four,
                 Integer five, Integer six, Integer seven, Integer eight,
                 Integer nine, Integer ten, Integer eleven, Integer twelve,
                 Integer r, Integer h, Integer e, Integer b, LocalDate date,
                 String note) {
        this.gameResult = gameResult;
        this.teamName = teamName;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.ten = ten;
        this.eleven = eleven;
        this.twelve = twelve;
        this.r = r;
        this.h = h;
        this.e = e;
        this.b = b;
        this.date = date;
        this.note = note;
    }
}
