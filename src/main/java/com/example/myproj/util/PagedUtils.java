package com.example.myproj.util;

import com.example.myproj.dto.page.Paged;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Utility class for {@link Paged}.
 *
 * @author Evgeny_Ageev
 */
@UtilityClass
public final class PagedUtils {

    public static <T> Paged<T> toPaged(Page<T> page) {
        return new Paged<>(page.getContent(), page.getTotalElements());
    }

    public static <T> Paged<T> toPaged(List<T> list) {
        return new Paged<>(list, list.size());
    }
}
