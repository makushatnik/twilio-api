package com.example.myproj.util;

import static java.util.Objects.nonNull;

import com.example.myproj.model.Location;
import com.example.myproj.repository.LocationRepository;
import lombok.experimental.UtilityClass;

import javax.persistence.EntityNotFoundException;

/**
 * LocationUtils.
 *
 * @author Evgeny_Ageev
 */
@UtilityClass
public final class LocationUtils {

    public static Location findLocation(Long locationId, LocationRepository repository) {
        if (nonNull(locationId)) {
            return repository.findById(locationId)
                             .orElseThrow(EntityNotFoundException::new);
        }
        return null;
    }
}
