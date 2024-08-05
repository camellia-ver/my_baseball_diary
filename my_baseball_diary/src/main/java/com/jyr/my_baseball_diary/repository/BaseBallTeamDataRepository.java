package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseBallTeamDataRepository extends JpaRepository<BaseballTeam, Long> {
}