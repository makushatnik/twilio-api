package com.example.myproj.controller;

import static com.example.myproj.util.CommonUtils.checkIfLongIdIsPositive;
import static com.example.myproj.util.CommonUtils.sendErrors;
import static com.example.myproj.util.PagedUtils.toPaged;
import static org.springframework.http.ResponseEntity.ok;

import com.example.myproj.controller.api.LocationControllerAPI;
import com.example.myproj.dto.location.LocationDto;
import com.example.myproj.dto.location.LocationResponse;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * LocationController.
 *
 * @author Evgeny_Ageev
 */
@RestController
@RequiredArgsConstructor
public class LocationController implements LocationControllerAPI {

    private final LocationService service;

    @Override
    public ResponseEntity<Paged<LocationResponse>> handleGetPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        return ok(toPaged(service.getAll(pageable)));
    }

    @Override
    public ResponseEntity<LocationResponse> handleCreate(@Valid @RequestBody LocationDto dto,
                                                         Errors errors) {
        sendErrors(errors);
        return ok(service.create(dto));
    }

//    @Override
//    public ResponseEntity<LocationResponse> handleUpdate(@PathVariable Long id,
//                                                         @Valid @RequestBody LocationDto dto,
//                                                         Errors errors) {
//        checkIfLongIdIsPositive(id);
//        sendErrors(errors);
//        return ok(service.update(id, dto));
//    }
}
