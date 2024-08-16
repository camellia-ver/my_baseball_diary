package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
public class DiaryForm {
    String title;
    String content;
    String location;
    String mvp;
    LocalDate gameDate;
    Time startGame;
}
