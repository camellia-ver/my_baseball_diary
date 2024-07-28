package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class AddTeamDataRequest {
    private String teamName;
    private String logoImage;
    private Year startYear;
    private String homeCity;
}
