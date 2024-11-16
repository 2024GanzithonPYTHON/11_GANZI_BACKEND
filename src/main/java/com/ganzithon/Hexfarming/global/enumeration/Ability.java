package com.ganzithon.Hexfarming.global.enumeration;

import lombok.Getter;

@Getter
public enum Ability {
    LEADERSHIP(0, "리더십", ""),
    PROBLEM_SOLVING(1, "", ""),
    COMMUNICATION_SKILL(2, "소통 역량", ""),
    DILIGENCE(3, "성실성", ""),
    RESILIENCE(4, "회복 탄력성", ""),
    TENACITY(5, "인성", "");


    private final int id;
    private final String name;
    private final String description;

    Ability(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
