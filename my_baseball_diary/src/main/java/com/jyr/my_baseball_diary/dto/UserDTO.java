package com.jyr.my_baseball_diary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String userName;
    private String favoriteTeam;
}
