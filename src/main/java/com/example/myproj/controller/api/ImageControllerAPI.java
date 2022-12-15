package com.example.myproj.controller.api;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.myproj.dto.ImageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ImageControllerAPI.
 *
 * @author Evgeny_Ageev
 */
@RequestMapping(value = API_PATH + "/img")
public interface ImageControllerAPI {

    @ApiOperation("Get page of images for particular post")
    @GetMapping(
            value = "/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Receive page of images for particular post"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<List<ImageDto>> handleGetAll(Long postId);

    @ApiOperation("Get image by id for particular post")
    @GetMapping(
            value = "/{postId}/{fileName}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Get particular image by id & postId"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Image is not found by id & postId"
                    )
            }
    )
    ResponseEntity<ImageDto> handleGet(Long postId, String fileName);

    @ApiOperation("Download image for particular post")
    @GetMapping(
            value = "/{postId}/{fileName:.+}/download",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Get particular image by id & postId"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Image is not found by id & postId"
                    )
            }
    )
    ResponseEntity<Resource> handleDownloadFile(Long postId, String fileName);

//    @ApiOperation("Upload image for particular post")
//    @PostMapping(
//            value = "/{postId}/upload",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = APPLICATION_JSON_VALUE
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            code = SC_OK,
//                            message = "Image has been uploaded"
//                    ),
//                    @ApiResponse(
//                            code = SC_BAD_REQUEST,
//                            message = "Invalid token or unsupported media type"
//                    ),
//                    @ApiResponse(
//                            code = SC_INTERNAL_SERVER_ERROR,
//                            message = "Internal server error occurred"
//                    )
//            }
//    )
//    ResponseEntity<ImageDto> handleFileUpload(Long postId, MultipartFile file);
//
//    @ApiOperation("Multi-upload images for particular post")
//    @PostMapping(
//            value = "/{postId}/multi-upload",
//            //consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            consumes = { "multipart/form-data" },
//            produces = APPLICATION_JSON_VALUE
//    )
//    @ApiResponses(
//            {
//                    @ApiResponse(
//                            code = SC_OK,
//                            message = "Images have been uploaded"
//                    ),
//                    @ApiResponse(
//                            code = SC_BAD_REQUEST,
//                            message = "Invalid token or unsupported media type"
//                    ),
//                    @ApiResponse(
//                            code = SC_INTERNAL_SERVER_ERROR,
//                            message = "Internal server error occurred"
//                    )
//            }
//    )
//    ResponseEntity<List<ImageDto>> handleMultiUpload(Long postId, MultipartFile[] files);

    @ApiOperation("Upload temporary image")
    @PostMapping(
            value = "/temp/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Image has been uploaded"
                    ),
                    @ApiResponse(
                            code = SC_BAD_REQUEST,
                            message = "Invalid token or unsupported media type"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<ImageDto> handleTempFileUpload(MultipartFile file);

    @ApiOperation("Multi-upload temporary images")
    @PostMapping(
            value = "/temp/multi-upload",
            //consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            consumes = { "multipart/form-data" },
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Images have been uploaded"
                    ),
                    @ApiResponse(
                            code = SC_BAD_REQUEST,
                            message = "Invalid token or unsupported media type"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<List<ImageDto>> handleTempMultiUpload(MultipartFile[] files);

    @ApiOperation("Upload temporary image in base64")
    @PostMapping(value = "/temp/base64/upload")
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Image has been uploaded"
                    ),
                    @ApiResponse(
                            code = SC_BAD_REQUEST,
                            message = "Invalid token or unsupported media type"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<ImageDto> handleTempFileUploadBase64(String file);
}
