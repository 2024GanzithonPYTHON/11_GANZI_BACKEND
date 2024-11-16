package com.ganzithon.Hexfarming.domain.experience;

import com.ganzithon.Hexfarming.domain.experience.dto.AbilityListServerDto;
import com.ganzithon.Hexfarming.domain.experience.dto.ExperienceServerDto;
import com.ganzithon.Hexfarming.domain.experience.dto.TierListServerDto;
import com.ganzithon.Hexfarming.domain.experience.util.ExperienceCreator;
import com.ganzithon.Hexfarming.domain.user.User;
import com.ganzithon.Hexfarming.domain.user.util.CustomUserDetailsService;
import com.ganzithon.Hexfarming.global.enumeration.Ability;
import com.ganzithon.Hexfarming.global.enumeration.Tier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository, CustomUserDetailsService customUserDetailsService) {
        this.experienceRepository = experienceRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional
    public void initiateAbilityExperience(User user) {
        List<Experience> experiences = Arrays.stream(Ability.values())
                        .map(ability -> ExperienceCreator.create(user, ability, Tier.TIER1_1))
                        .collect(Collectors.toList());
        experienceRepository.saveAll(experiences);
    }

    public List<AbilityListServerDto> getAbilityList() {
        return Arrays.stream(Ability.values())
                .map(AbilityListServerDto::from)
                .collect(Collectors.toList());
    }

    public List<TierListServerDto> getTierList() {
        return Arrays.stream(Tier.values())
                .map(TierListServerDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExperienceServerDto> getMyExperiences() {
        int nowUserId = customUserDetailsService.getCurrentUserDetails().getUser().getId();
        return getExperiences(nowUserId);
    }

    @Transactional(readOnly = true)
    public List<ExperienceServerDto> getExperiences(int userId) {
        List<Experience> experiences = experienceRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저id입니다."));

        return experiences.stream()
                .map(ExperienceServerDto::from)
                .collect(Collectors.toList());
    }
}