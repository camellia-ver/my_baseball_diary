package com.jyr.my_baseball_diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

@Table(name = "baseball_team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BaseballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "logo_image", nullable = false)
    private String logoImage;

    @Column(name = "start_year", nullable = false)
    private Year startYear;

    @Column(name = "home_city", nullable = false)
    private String homeCity;

    @Builder
    public BaseballTeam(String teamName, String logoImage,
                        Year startYear, String homeCity) {
        this.teamName = teamName;
        this.logoImage = logoImage;
        this.startYear = startYear;
        this.homeCity = homeCity;
    }
}
