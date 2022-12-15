package com.example.myproj.controller.api;

import com.example.myproj.dto.sms.*;
import com.example.myproj.dto.user.UserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface LoginControllerAPI {

    @ApiOperation("1st Step. Get request for a sms verification code")
    @PostMapping(
            value = API_PATH + "login/sms",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "Code sent to mobile"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<LoginResponse> loginRequest(LoginDto loginDto, @ApiIgnore HttpSession session,
                                               @ApiIgnore Errors errors);

    @ApiOperation("2nd Step. Confirm a verification code")
    @PostMapping(
            value = API_PATH + "login/confirm",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "User authenticated or need to register first"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<?> loginConfirm(ConfirmRequest confirmDto, @ApiIgnore HttpSession session,
                                   @ApiIgnore Errors errors);

    @ApiOperation("3th Step. Get request for registering new user")
    @PostMapping(
            value = API_PATH + "login/register",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            code = SC_OK,
                            message = "User registered & authenticated"
                    ),
                    @ApiResponse(
                            code = SC_INTERNAL_SERVER_ERROR,
                            message = "Internal server error occurred"
                    )
            }
    )
    ResponseEntity<TokenDto> registerRequest(RegisterDto registerDto, @ApiIgnore HttpSession session,
                                                 @ApiIgnore Errors errors);

}
