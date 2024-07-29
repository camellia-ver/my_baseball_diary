package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import com.jyr.my_baseball_diary.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseBallDataRepository extends JpaRepository<BaseballTeam, Long> {
}