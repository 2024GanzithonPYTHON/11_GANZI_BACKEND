package com.ganzithon.Hexfarming.global.enumeration;

import lombok.Getter;

@Getter
public enum Tier {
    Tear1_1("Tier1 ★", 50),
    Tear1_2("Tier1 ★★", 60),
    Tear1_3("Tier1 ★★★", 72),
    Tear1_4("Tier1 ★★★★", 86),
    Tear1_5("Tier1 ★★★★★", 104),

    Tear2_1("Tier2 ★", 156),
    Tear2_2("Tier2 ★★", 187),
    Tear2_3("Tier2 ★★★", 224),
    Tear2_4("Tier2 ★★★★", 269),
    Tear2_5("Tier2 ★★★★★", 322),

    Tear3_1("Tier3 ★", 484),
    Tear3_2("Tier3 ★★", 580),
    Tear3_3("Tier3 ★★★", 697),
    Tear3_4("Tier3 ★★★★", 836),
    Tear3_5("Tier3 ★★★★★", 1003),

    Tear4_1("Tier4 ★", 1505),
    Tear4_2("Tier4 ★★", 1806),
    Tear4_3("Tier4 ★★★", 2167),
    Tear4_4("Tier4 ★★★★", 2600),
    Tear4_5("Tier4 ★★★★★", 3120),

    Tear5_1("Tier5 ★", 4680),
    Tear5_2("Tier5 ★★", 5616),
    Tear5_3("Tier5 ★★★", 6739),
    Tear5_4("Tier5 ★★★★", 8087),
    Tear5_5("Tier5 ★★★★★", 9704);


    private final String name;
    private final int requiredExperience;

    Tier(String name, int requiredExperience) {
        this.name = name;
        this.requiredExperience = requiredExperience;
    }
}
