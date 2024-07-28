package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.dto.AddUserRequest;
import com.jyr.my_baseball_diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.join(request);
        return "redirect:/user/login";
    }
}
