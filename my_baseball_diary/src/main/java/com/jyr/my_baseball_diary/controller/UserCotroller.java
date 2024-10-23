package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.service.BaseballTeamService;
import com.jyr.my_baseball_diary.service.UserService;
import com.jyr.my_baseball_diary.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class UserCotroller {
    private final BaseballTeamService baseballTeamService;
    private final UserService userService;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("TeamData",baseballTeamService.findTeamAll());
        return "signup";
    }

    @PostMapping("/user")
    public String signup(UserDTO request, Model model) {
        try {
            userService.join(request);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "redirect:/login";
    }

    @PostMapping("/userUpdate")
    public String userUpdate(UserDTO request) {
        userService.update(request);
        return "redirect:/myPage";
    }

    @GetMapping("/myPage")
    public String myPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByUser(email);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("date",formatter.format(user.getCreateDate()));
        model.addAttribute("user", user);

        return "myPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
