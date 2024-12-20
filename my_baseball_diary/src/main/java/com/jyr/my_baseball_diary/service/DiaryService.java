package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.DiaryDTO;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.repository.GameDataRepository;
import com.jyr.my_baseball_diary.repository.LineUpRepository;
import com.jyr.my_baseball_diary.repository.UserRepository;
import com.jyr.my_baseball_diary.utill.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final CommonUtils commonUtils;

    @Transactional
    public Long save(DiaryDTO dto) {
        Time startGameTime = parseStartGameTime(dto.getStartGame().replace(",",""));

        Diary diary = Diary.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .mvp(dto.getMvp())
                .date(LocalDateTime.now())
                .startGame(startGameTime)
                .gameDate(dto.getGameDate())
                .user(commonUtils.getCurrentUser().orElse(null))
                .build();

        return diaryRepository.save(diary).getId();
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(startGame, formatter);
            return Time.valueOf(localTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식: " + startGame, e);
        }
    }

    public Boolean isGame(LocalDate date, String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .anyMatch(gameData -> gameData.getTeamName().equals(teamName));
    }

    public Boolean isDoubleHeader(LocalDate date, String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .count() > 1;
    }

    public Time findStartGame(LocalDate date, String teamName, String selectGame) {
        List<Time> result = new ArrayList<>();

        List<GameData> gameDataList = gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .toList();

        for (GameData gameData : gameDataList) {
            result.add(gameData.getStartGame());
        }

        if (result.isEmpty()) {
            throw new IllegalStateException("No games found for the given date and team.");
        }

        if (result.size() == 1) {
            return result.get(0);
        }

        Time time1 = result.get(0);
        Time time2 = result.get(1);

        if ("first".equals(selectGame)) {
            return time1.compareTo(time2) < 0 ? time1 : time2;
        } else if ("second".equals(selectGame)) {
            return time1.compareTo(time2) > 0 ? time1 : time2;
        } else {
            throw new IllegalArgumentException("Invalid value for selectGame: " + selectGame);
        }
    }

    public Map<String, List<LineUp>> findLineUp(LocalDate date, String teamName) {
        return divideLineUp(lineUpRepository.findByDate(date).stream()
                .filter(lineUp -> lineUp.getTeamName().equals(teamName))
                .collect(Collectors.toList()));
    }

    public Map<String, List<LineUp>> findLineUpByGameStart(LocalDate date, String teamName, Time gameStart) {
        System.out.println(gameStart);

        return divideLineUp(lineUpRepository.findByDate(date).stream()
                .filter(lineUp -> lineUp.getTeamName().equals(teamName))
                .filter(lineUp -> lineUp.getStartGame().equals(gameStart))
                .collect(Collectors.toList()));
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

    private GameData findTeamData(List<GameData> gameDataList, String teamName, Time startGame) {
        return gameDataList.stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .filter(gameData -> startGame == null || gameData.getStartGame().equals(startGame))
                .findFirst()
                .orElse(null);
    }

    public Map<String, GameData> findGameData(LocalDate date, String teamName) {
        List<GameData> gameDataList = gameDataRepository.findByDate(date);
        GameData teamData = findTeamData(gameDataList, teamName, null);

        Map<String, GameData> result = new LinkedHashMap<>();
        if (teamData != null) {
            result.put("team1", teamData);

            String matchTeam = teamData.getMatchTeam();
            GameData opponentData = findTeamData(gameDataList, matchTeam, null);

            result.put("team2", opponentData);
        } else {
            result.put("team1", null);
            result.put("team2", null);
        }

        return result;
    }

    public Map<String, GameData> findGameDataByStartGame(LocalDate date, String teamName, Time startGame) {
        List<GameData> gameDataList = gameDataRepository.findByDate(date);
        GameData teamData = findTeamData(gameDataList, teamName, startGame);

        Map<String, GameData> result = new LinkedHashMap<>();
        if (teamData != null) {
            result.put("team1", teamData);

            String matchTeam = teamData.getMatchTeam();
            GameData opponentData = findTeamData(gameDataList, matchTeam, null);

            result.put("team2", opponentData);
        } else {
            result.put("team1", null);
            result.put("team2", null);
        }

        return result;
    }

    public Diary findDiaryData(LocalDate date, Long userId) {
        return diaryRepository.findByGameDate(date).stream()
                .filter(diary -> diary.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public Page<Diary> findByUserId(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Long userId = commonUtils.getCurrentUser().orElseThrow().getId();
        return diaryRepository.findByUserId(userId, pageable);
    }

    public Diary findById(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        diaryRepository.deleteById(id);
    }

    public Long countTodayCreatedDiary() {
        LocalDate today = LocalDate.now();
        return diaryRepository.countByDate(today);
    }
}
