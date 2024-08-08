package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "mvp", nullable = false)
    private String mvp;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Diary(String title, String content, String location,
                 String mvp, LocalDate date, String note) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.mvp = mvp;
        this.date = date;
        this.note = note;
    }
}
