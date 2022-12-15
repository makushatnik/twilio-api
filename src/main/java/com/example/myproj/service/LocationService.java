package com.example.myproj.service;

import com.example.myproj.dto.location.LocationDto;
import com.example.myproj.dto.location.LocationResponse;
import com.example.myproj.mapper.LocationMapper;
import com.example.myproj.model.Location;
import com.example.myproj.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * LocationService.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationMapper mapper;
    private final LocationRepository repository;

    @Transactional(readOnly = true)
    public Page<LocationResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                         .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Location findByCoordinates(String latitude, String longitude) {
        List<Location> list = repository.findByLatitudeAndLongitude(latitude, longitude);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Transactional
    public LocationResponse create(LocationDto dto) {
        Location location = mapper.toModel(dto);
        return mapper.toResponse(repository.save(location));
    }

    @Transactional
    public Location findOrCreate(LocationDto dto) {
        Location location = findByCoordinates(dto.getMapView().getLatitude(),
                dto.getMapView().getLongitude());
        if (isNull(location)) {
            location = createInner(dto);
        }
        return location;
    }

    private Location createInner(LocationDto dto) {
        return repository.save(mapper.toModel(dto));
    }

//    public LocationResponse update(Long id, LocationDto dto) {
//        Location found = repository.findById(id)
//                                   .orElseThrow(EntityNotFoundException::new);
//
//        found.setCity(dto.getCity())
//                .setCountry(dto.getCountry())
//                .setState(dto.getState())
//                .setLatitude(dto.getLatitude())
//                .setLongitude(dto.getLongitude())
//                .setZipCode(dto.getZipCode());
//
//        return mapper.toResponse(repository.save(found))
//                     .setUpdatedAt(LocalDateTime.now());
//    }
}
