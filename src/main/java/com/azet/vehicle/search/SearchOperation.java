package com.azet.vehicle.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@AllArgsConstructor
public enum SearchOperation {
    EQUALITY(":"),
    GREATER_THAN(">"),
    LESS_THAN("<");

    @Getter
    private final String type;

    public static SearchOperation of(String value) {
        return Arrays.stream(values())
                .filter(reason -> reason.getType().equals(value))
                .findFirst()
                .orElse(null);
    }
}
