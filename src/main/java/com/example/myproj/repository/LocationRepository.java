package com.example.myproj.repository;

import com.example.myproj.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * LocationRepository.
 *
 * @author Evgeny_Ageev
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByLatitudeAndLongitude(String latitude, String longitude);
}
