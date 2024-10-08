package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
public class DiaryDTO {
    private Long id;
    private String title;
    private String content;
    private String mvp;
    private LocalDate gameDate;
    private String startGame;
}
