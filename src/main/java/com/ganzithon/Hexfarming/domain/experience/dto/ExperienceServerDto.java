package com.ganzithon.Hexfarming.domain.experience.dto;

import com.ganzithon.Hexfarming.domain.experience.Experience;
import com.ganzithon.Hexfarming.global.enumeration.Ability;
import com.ganzithon.Hexfarming.global.enumeration.Tier;

public record ExperienceServerDto(Ability ability, Tier tier, int experience) {

    public static ExperienceServerDto from(Experience experience) {
        return new ExperienceServerDto(
                experience.getAbility(),
                experience.getTier(),
                experience.getExperience()
        );
    }
}
