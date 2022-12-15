package com.example.myproj.controller.api;

import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.dto.post.TempLikeDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = API_PATH + "/post/unregistered")
public interface UnregisteredPostControllerAPI {

    @ApiOperation("Get page of active posts")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of active posts by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetActivePage(Integer number, Integer size);

    @ApiOperation("Get post by id")
    @GetMapping(
            value = "/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Get particular post by its identifier"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleGet(Long id);

    @ApiOperation("Report of abuse by post id")
    @PatchMapping(
            value = "/{id}/report",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been reported"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleReport(Long id, String complaint);

    @ApiOperation("Like/dislike post by id")
    @PatchMapping(
            value = "/like",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been liked/disliked"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<Void> handleLike(TempLikeDto dto, @ApiIgnore Errors errors);

    @ApiOperation("Share post by id")
    @PatchMapping(
            value = "/{id}/share",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been prepared to be shared"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleShare(Long id);

    @ApiOperation("Get page of temp picks")
    @GetMapping(
            value = "/picks",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of temporary picks"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PickDto>> handleGetTempLikes(String drive, Integer page, Integer size);
}
