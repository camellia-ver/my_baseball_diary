package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;

@Repository
public interface BaseBallTeamDataRepository extends JpaRepository<BaseballTeam, Long> {
    @Modifying
    @Query("UPDATE BaseballTeam b SET b.teamName = ?1, b.logoImage = ?2, b.startYear = ?3, b.homeCity = ?4 WHERE b.id = ?5")
    void updateBaseballTeam(String teamName, String logoImage, Year startYear, String homeCity, Long id);
}