package com.example.myproj.controller.api;

import com.example.myproj.dto.chat.MessageRequest;
import com.example.myproj.dto.chat.MessageResponse;
import com.example.myproj.dto.chat.ThreadDto;
import com.example.myproj.dto.page.Paged;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = API_PATH + "/thread")
public interface ThreadControllerAPI {

    @ApiOperation("Get page of threads")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Get page of threads for current user"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Paged<ThreadDto>> handleGetThreadList(Integer page, Integer size);

    @ApiOperation("Get page of messages")
    @GetMapping(
            value = "/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Get page of messages by threadId"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Image is not found by id & postId"
                    )
            }
    )
    ResponseEntity<Paged<MessageResponse>> handleGetMessages(Long id, Integer page, Integer size);

    @ApiOperation("Send message")
    @PostMapping(
            value = "/message",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Send a message"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<MessageResponse> handleSend(MessageRequest dto, @ApiIgnore Errors errors);

    @ApiOperation("Report of abuse by user id")
    @PostMapping(
            value = "/{id}/report",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "User has been reported"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "User is not found by identifier"
                    )
            }
    )
    ResponseEntity handleReport(Long id, String complaint);

    @ApiOperation("Delete thread by id")
    @DeleteMapping("/id/{id}")
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Thread has been deleted"
                    ),
                    @ApiResponse(
                            code = SC_NOT_FOUND,
                            message = "Thread is not found by identifier"
                    )
            }
    )
    ResponseEntity<Void> handleDeleteById(Long id);

    @ApiOperation("Delete thread by sid")
    @DeleteMapping("/sid/{sid}")
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Thread has been deleted"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<Void> handleDeleteBySid(String sid);
}
