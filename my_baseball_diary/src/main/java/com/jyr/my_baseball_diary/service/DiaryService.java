package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.DiaryDTO;
import com.jyr.my_baseball_diary.dto.DiaryListDTO;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.repository.GameDataRepository;
import com.jyr.my_baseball_diary.repository.LineUpRepository;
import com.jyr.my_baseball_diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final GameDataRepository gameDataRepository;
    private final LineUpRepository lineUpRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(DiaryDTO dto) {
        Time startGameTime = parseStartGameTime(dto.getStartGame().replace(",",""));

        Diary diary = Diary.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .mvp(dto.getMvp())
                .date(LocalDateTime.now())
                .startGame(startGameTime)
                .gameDate(dto.getGameDate())
                .user(getCurrentUser().orElse(null))
                .build();

        diaryRepository.save(diary);
    }

    @Transactional
    public void update(DiaryDTO dto) {
        Time startGameTime = parseStartGameTime(dto.getStartGame().replace(",",""));

        diaryRepository.updateDiary(dto.getId(), dto.getTitle(), dto.getContent(),
                dto.getMvp(), LocalDateTime.now(), startGameTime, dto.getGameDate());
    }

    private Time parseStartGameTime(String startGame) {
        if (startGame == null || startGame.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(startGame, formatter);
            return Time.valueOf(localTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식: " + startGame, e);
        }
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByEmail(username);
        }
        return Optional.empty();
    }

    public Boolean isGame(LocalDate date,String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .count() == 1;
    }

    public Boolean isDoubleHeader(LocalDate date, String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .count() > 1;
    }

    public Map<String, List<LineUp>> findLineUp(LocalDate date, String teamName) {
        return divideLineUp(lineUpRepository.findByDate(date).stream()
                .filter(lineUp -> lineUp.getTeamName().equals(teamName))
                .collect(Collectors.groupingBy(LineUp::getStartGame))
                .entrySet().stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(Collections.emptyList()));
    }

    private Map<String, List<LineUp>> divideLineUp(List<LineUp> lineUpList) {
        List<LineUp> pList = new ArrayList<>();
        List<LineUp> hList = new ArrayList<>();

        for (LineUp lineUp : lineUpList) {
            if (lineUp.getPosition().equals("투수")) {
                pList.add(lineUp);
            } else {
                hList.add(lineUp);
            }
        }

        Map<String, List<LineUp>> result = new LinkedHashMap<>();
        result.put("pitcher", pList);
        result.put("hitter", hList);

        return result;
    }

    public Map<String, GameData> findGameData(LocalDate date, String teamName) {
        List<GameData> gameDataList = gameDataRepository.findByDate(date);
        GameData teamData = gameDataList.stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .min(Comparator.comparing(GameData::getStartGame))
                .orElse(null);

        Map<String, GameData> result = new LinkedHashMap<>();

        if (teamData != null) {
            result.put("team1", teamData);

            String matchTeam = teamData.getMatchTeam();
            GameData opponentData = gameDataList.stream()
                    .filter(gameData -> gameData.getTeamName().equals(matchTeam))
                    .min(Comparator.comparing(GameData::getStartGame))
                    .orElse(null);

            result.put("team2", opponentData);
        } else {
            result.put("team1", null);
            result.put("team2", null);
        }

        return result;
    }

    public Diary findDiaryDataNotDoubleHeader(LocalDate date, Long userId) {
        return diaryRepository.findByGameDate(date).stream()
                .filter(diary -> diary.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public Diary findDiaryDataDoubleHeader(LocalDate date, Long userId,Time startGame) {
        return diaryRepository.findByGameDate(date).stream()
                .filter(diary -> diary.getUser().getId().equals(userId))
                .filter(diary -> diary.getStartGame().equals(startGame))
                .findFirst()
                .orElse(null);
    }

    public List<DiaryListDTO> findById() {
        Long id = getCurrentUser().orElseThrow().getId();
        List<Diary> diaryList = diaryRepository.findByUserId(id).stream().toList();
        List<DiaryListDTO> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Diary d : diaryList) {
            DiaryListDTO temp = new DiaryListDTO();
            temp.setId(d.getId());
            temp.setTitle(d.getTitle());
            temp.setGameDate(d.getGameDate());
            temp.setStartGame(d.getStartGame());
            temp.setFormattedDate(d.getDate().format(formatter));
            result.add(temp);
        }

        return result;
    }
}
