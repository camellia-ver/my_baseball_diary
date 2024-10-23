package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

@Getter
@Setter
public class BaseballTeamDTO {
    private String teamName;
    private MultipartFile logoImage;
    private Year startYear;
    private String homeCity;
}
