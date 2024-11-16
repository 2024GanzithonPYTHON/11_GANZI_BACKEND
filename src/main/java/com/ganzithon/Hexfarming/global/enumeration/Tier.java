package com.ganzithon.Hexfarming.global.enumeration;

import lombok.Getter;

@Getter
public enum Tier {
    TIER1_1("Tier1 ★", 50),
    TIER1_2("Tier1 ★★", 60),
    TIER1_3("Tier1 ★★★", 72),
    TIER1_4("Tier1 ★★★★", 86),
    TIER1_5("Tier1 ★★★★★", 104),

    TIER2_1("Tier2 ★", 156),
    TIER2_2("Tier2 ★★", 187),
    TIER2_3("Tier2 ★★★", 224),
    TIER2_4("Tier2 ★★★★", 269),
    TIER2_5("Tier2 ★★★★★", 322),

    TIER3_1("Tier3 ★", 484),
    TIER3_2("Tier3 ★★", 580),
    TIER3_3("Tier3 ★★★", 697),
    TIER3_4("Tier3 ★★★★", 836),
    TIER3_5("Tier3 ★★★★★", 1003),

    TIER4_1("Tier4 ★", 1505),
    TIER4_2("Tier4 ★★", 1806),
    TIER4_3("Tier4 ★★★", 2167),
    TIER4_4("Tier4 ★★★★", 2600),
    TIER4_5("Tier4 ★★★★★", 3120),

    TIER5_1("Tier5 ★", 4680),
    TIER5_2("Tier5 ★★", 5616),
    TIER5_3("Tier5 ★★★", 6739),
    TIER5_4("Tier5 ★★★★", 8087),
    TIER5_5("Tier5 ★★★★★", 9704);


    private final String name;
    private final int requiredExperience;

    Tier(String name, int requiredExperience) {
        this.name = name;
        this.requiredExperience = requiredExperience;
    }
}
