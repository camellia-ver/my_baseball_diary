package com.jyr.my_baseball_diary.api;

import com.jyr.my_baseball_diary.service.BaseballTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BaseballTeamApiController {
    private final BaseballTeamService baseballTeamService;

    @DeleteMapping("/api/baseballTeam/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("id") Long id) {
        baseballTeamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
