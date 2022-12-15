package com.example.myproj.controller.api;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.myproj.dto.location.LocationDto;
import com.example.myproj.dto.location.LocationResponse;
import com.example.myproj.dto.page.Paged;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * LocationControllerAPI.
 *
 * @author Evgeny_Ageev
 */
@RequestMapping(value = API_PATH + "/location")
public interface LocationControllerAPI {

    @ApiOperation("Get page of locations")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of locations by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<LocationResponse>> handleGetPage(Integer number, Integer size);

    @ApiOperation("Create new location")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Location has been created"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<LocationResponse> handleCreate(LocationDto dto, @ApiIgnore Errors errors);

//    @ApiOperation("Update location by id")
//    @PutMapping(
//            value = "/{id}",
//            produces = APPLICATION_JSON_VALUE
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            code = SC_OK,
//                            message = "Location has been updated"
//                    ),
//                    @ApiResponse(
//                            code = SC_NOT_FOUND,
//                            message = "Location is not found by identifier"
//                    )
//            }
//    )
//    ResponseEntity<LocationResponse> handleUpdate(Long id, LocationDto dto, @ApiIgnore Errors errors);
}
