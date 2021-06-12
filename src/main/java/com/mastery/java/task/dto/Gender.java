package com.mastery.java.task.dto;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender fromString(String gender) {
        return Arrays.stream(Gender.values())
                .filter(genderValue -> genderValue.name().equalsIgnoreCase(gender))
                .findFirst()
                .orElse(FEMALE);
    }
}
