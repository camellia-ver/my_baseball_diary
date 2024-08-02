package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import com.jyr.my_baseball_diary.repository.BaseBallDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaseballDataService {
    private final BaseBallDataRepository baseBallDataRepository;

    public List<BaseballTeam> findTeamAll() {
        return baseBallDataRepository.findAll();
    }
}
