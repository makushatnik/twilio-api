package com.example.myproj.mapper;

import static com.example.myproj.util.CommonConstants.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.example.myproj.dto.location.LocationDto;
import com.example.myproj.dto.location.LocationResponse;
import com.example.myproj.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * LocationMapper.
 *
 * @author Evgeny_Ageev
 */
@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING
)
public interface LocationMapper {

    @Mapping(target = "latitude", source = "mapView.latitude")
    @Mapping(target = "longitude", source = "mapView.longitude")
    Location toModel(LocationDto dto);

    @Named("toDto")
    @Mapping(target = "mapView.latitude", source = "latitude")
    @Mapping(target = "mapView.longitude", source = "longitude")
    LocationDto toDto(Location location);

    LocationResponse toResponse(Location location);
}
