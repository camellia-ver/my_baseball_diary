package com.jyr.my_baseball_diary.api;

import com.jyr.my_baseball_diary.service.UserService;
import com.jyr.my_baseball_diary.dto.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserForm request, Model model) {
        try {
            userService.join(request);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "redirect:/login";
    }
}
