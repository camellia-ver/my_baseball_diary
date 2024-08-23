package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DiaryListDTO {
    private Long id;
    private String title;
    private LocalDate gameDate;
    private Time startGame;
    private String formattedDate;
}
