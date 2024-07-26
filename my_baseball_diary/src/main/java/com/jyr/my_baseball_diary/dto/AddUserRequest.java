package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String passwrod;
    private String userName;
    private Long favoriteTeamId;
    private LocalDateTime createDate;
}
