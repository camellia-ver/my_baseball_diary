package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.GameData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {
    @Query("SELECT g FROM GameData g WHERE g.date = :date")
    List<GameData> findByDate(@Param("date") LocalDate date);

    Boolean existsByDate(LocalDate date);

    @Query("SELECT g.date FROM GameData g ORDER BY g.date DESC")
    List<LocalDate> findTopDates();
}
