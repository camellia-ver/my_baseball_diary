package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import com.jyr.my_baseball_diary.dto.BaseballTeamDTO;
import com.jyr.my_baseball_diary.repository.BaseBallTeamDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.AssertFalse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaseballTeamService {
    private static final Logger logger = LoggerFactory.getLogger(Example.class);
    private final BaseBallTeamDataRepository baseBallTeamDataRepository;

    public List<BaseballTeam> findBaseballTeamAll() {
        return baseBallTeamDataRepository.findAll();
    }

    public BaseballTeam findBaseballTeamById(Long id) {
        return baseBallTeamDataRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Baseball team not found with id: " + id));
    }

    @Transactional
    public void save(BaseballTeamDTO dto) {
        String imagePath = saveImage(dto.getLogoImage());

        baseBallTeamDataRepository.save(BaseballTeam.builder()
                .teamName(dto.getTeamName())
                .logoImage(imagePath)
                .startYear(dto.getStartYear())
                .homeCity(dto.getHomeCity())
                .build());
    }

    @Transactional
    public void update(BaseballTeamDTO dto, Boolean isLogoPath) {
        String imagePath;
        if (isLogoPath) {
            imagePath = saveImage(dto.getLogoImage());
        } else {
            imagePath = findBaseballTeamById(dto.getId()).getLogoImage();
        }

        baseBallTeamDataRepository.updateBaseballTeam(dto.getTeamName(), imagePath, dto.getStartYear(), dto.getHomeCity(), dto.getId());
    }

    private String saveImage(MultipartFile file) {
        String uploadDir = "path/to/upload/directory/";
        String filePath = uploadDir + file.getOriginalFilename();

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            logger.error("An error occurred while processing", e);
        }

        return filePath;
    }
}
