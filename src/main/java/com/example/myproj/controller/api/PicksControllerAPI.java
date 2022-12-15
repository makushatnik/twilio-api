package com.example.myproj.controller.api;

import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.enums.PostState;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = API_PATH + "/picks")
public interface PicksControllerAPI {

    @ApiOperation("Get page of won picks")
    @GetMapping(
            value = "/won",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of won picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetWonPage(Integer number, Integer size);

    @ApiOperation("Get page of lost picks")
    @GetMapping(
            value = "/lost",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of lost picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetLostPage(Integer number, Integer size);

    @ApiOperation("Get page of all picks")
    @GetMapping(
            value = "/all",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of all picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetAll(Integer number, Integer size);

    @ApiOperation("Get page of own picks")
    @GetMapping(
            value = "/own",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of own picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetOwn(Integer number, Integer size);

    @ApiOperation("Get page of own picks by state")
    @GetMapping(
            value = "/own/{state}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of own picks by state"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetOwnByState(PostState state, Integer number, Integer size);

    @ApiOperation("Get page of own picks by post id")
    @GetMapping(
            value = "/{postId}/own",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of own picks by post id"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetOwnByPostId(Long postId, Integer number, Integer size);

    @ApiOperation("Get page of liked picks")
    @GetMapping(
            value = "/liked",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetLiked(Integer number, Integer size);

    @ApiOperation("Get page of liked picks by state")
    @GetMapping(
            value = "/liked/{state}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of picks by state"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetLikedByState(PostState state, Integer number, Integer size);

    @ApiOperation("Get page of liked picks by post id")
    @GetMapping(
            value = "/{postId}/liked",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of picks by post id"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetLikedByPostId(Long postId, Integer number, Integer size);
}
