package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.dto.AddUserRequest;
import com.jyr.my_baseball_diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        Long result = userService.join(request);
        return "redirect:/login";
    }

    @PostMapping("/validateEmail")
    public ResponseEntity<?> vailedateEmail(@RequestParam String email) {
        boolean result = !userService.checkEmail(email);

        return ResponseEntity.ok(result);
    }
}
