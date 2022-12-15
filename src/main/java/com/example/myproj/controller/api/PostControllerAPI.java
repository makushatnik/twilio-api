package com.example.myproj.controller.api;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.myproj.dto.FilterDto;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.post.PostRequest;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.enums.Category;
import com.example.myproj.enums.PostRate;
import com.example.myproj.enums.PostState;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

/**
 * PostControllerAPI.
 *
 * @author Evgeny_Ageev
 */
@RequestMapping(value = API_PATH + "/post")
public interface PostControllerAPI {

    @ApiOperation("Get page by filters")
    @PostMapping(
            value = "/list",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of posts by filter"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetPage(FilterDto dto);

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

    @ApiOperation("Get page of own posts")
    @GetMapping(
            value = "/own",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of own posts by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetOwnPage(Integer number, Integer size, PostState state);

    @ApiOperation("Get page of won posts")
    @GetMapping(
            value = "/won",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of won posts by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetWonPage(Integer number, Integer size);

    @ApiOperation("Get page of lost posts")
    @GetMapping(
            value = "/lost",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of lost posts by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetLostPage(Integer number, Integer size);

    @ApiOperation("Get page of archived posts")
    @GetMapping(
            value = "/archived",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of archived posts by page number & page size"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<PostResponse>> handleGetArchivedPage(Integer number, Integer size);

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

    @ApiOperation("Create new post")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been created"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<PostResponse> handleCreate(PostRequest dto, @ApiIgnore Errors errors);

    @ApiOperation("Attach images to a post")
    @PutMapping(
            value = "/{id}/attach",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Images have been attached"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<PostResponse> handleAttach(Long id, List<Long> imageIds);

//    @ApiOperation("Create post with files")
//    @PostMapping(
//            value = "/multi-upload",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//            //consumes = { "multipart/form-data" }
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            code = SC_OK,
//                            message = "Post has been created & images was uploaded"
//                    ),
//                    @ApiResponse(
//                            code = SC_BAD_REQUEST,
//                            message = "Required argument isn't set or invalid token or unsupported media type"
//                    ),
//                    @ApiResponse(
//                            code = SC_INTERNAL_SERVER_ERROR,
//                            message = "Internal server error occurred"
//                    )
//            }
//    )
//    ResponseEntity<PostResponse> handleCreateWithFiles(PostRequest dto);

    @ApiOperation("Update post by id")
    @PutMapping(
            value = "/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been updated"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleUpdate(Long id, PostRequest dto, @ApiIgnore Errors errors);

    @ApiOperation("Renew post by id")
    @PatchMapping(
            value = "/{id}/renew",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been renewed"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleRenew(Long id);

    @ApiOperation("Give away item to buyer")
    @PatchMapping(
            value = "/{id}/giveaway",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Give away item with id to buyer by his id"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<PostResponse> handleGiveAway(Long id);

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
            value = "/{id}/like",
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
    ResponseEntity<PostResponse> handleLike(Long id, PostRate rate);

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

    @ApiOperation("Delete post by id")
    @DeleteMapping("/{id}")
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Post has been deleted"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Post is not found by identifier"
                    )
            }
    )
    ResponseEntity<Void> handleDelete(Long id);

    @ApiOperation("Get category set")
    @GetMapping(
            value = "/categories",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive set of all available categories"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Set<Category>> handleGetCategories();
}
