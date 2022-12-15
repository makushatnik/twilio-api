package com.example.myproj.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Paged data.
 *
 * @author Evgeny_Ageev
 */
@Getter
@Setter
@AllArgsConstructor
public class Paged<T> {

    /**
     * Data.
     */
    private List<T> data;

    /**
     * Total count of data.
     */
    private long total;
}
