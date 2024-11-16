package com.ganzithon.Hexfarming.global.enumeration;

import lombok.Getter;

@Getter
public enum Ability {
    LEADERSHIP("리더십", ""),
    PROBLEM_SOLVING("", ""),
    COMMUNICATION_SKILL("소통 역량", ""),
    DILIGENCE("성실성", ""),
    RESILIENCE("회복 탄력성", ""),
    TENACITY("인성", "");


    private final String name;
    private final String description;

    Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
