package com.example.myproj.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Condition.
 *
 * @author Evgeny_Ageev
 */
@Getter
@RequiredArgsConstructor
public enum Condition {
    NEW("New"),
    USED("Used"),
    BROKEN("Needs repair");

    private final String description;

}
