package com.ganzithon.Hexfarming.domain.experience;

import com.ganzithon.Hexfarming.domain.experience.util.ExperienceCreator;
import com.ganzithon.Hexfarming.domain.user.User;
import com.ganzithon.Hexfarming.domain.user.util.CustomUserDetailsService;
import com.ganzithon.Hexfarming.global.enumeration.Ability;
import com.ganzithon.Hexfarming.global.enumeration.Tier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
}
