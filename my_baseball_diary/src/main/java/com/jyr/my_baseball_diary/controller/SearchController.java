package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public String search(Model model,
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        model.addAttribute("diaryList", searchService.search(query,page,size));
        model.addAttribute("query", query);

        return "searchResults";
    }
}
