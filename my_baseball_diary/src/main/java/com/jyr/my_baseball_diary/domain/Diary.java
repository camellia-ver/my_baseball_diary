package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "diary")
@Getter
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "mvp")
    private String mvp;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "start_game")
    private Time startGame;

    @Column(name = "game_date")
    private LocalDate gameDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Diary() {}

    @Builder
    public Diary(String title, String content, String mvp, LocalDateTime date,
                 Time startGame, LocalDate gameDate, User user) {
        this.title = title;
        this.content = content;
        this.mvp = mvp;
        this.date = date;
        this.startGame = startGame;
        this.gameDate = gameDate;
        this.user = user;
    }
}
