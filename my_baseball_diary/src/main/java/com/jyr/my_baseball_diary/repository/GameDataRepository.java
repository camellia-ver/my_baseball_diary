package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {
    List<GameData> findByDate(LocalDate date);

    Boolean existsByDate(LocalDate date);

    LocalDate findTopByOrderByDateDesc();
}
