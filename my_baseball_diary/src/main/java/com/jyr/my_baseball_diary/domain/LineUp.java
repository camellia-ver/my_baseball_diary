package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;

@Table(name = "line_up")
@Getter
@Entity
public class LineUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "no")
    private Integer no;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_game")
    private Time startGame;

    @Builder
    public LineUp(String teamName, Integer no, String position, String playerName,
                  LocalDate date, Time startGame) {
        this.teamName = teamName;
        this.no = no;
        this.position = position;
        this.playerName = playerName;
        this.date = date;
        this.startGame = startGame;
    }
}
