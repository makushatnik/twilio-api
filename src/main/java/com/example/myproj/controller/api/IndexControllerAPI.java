package com.example.myproj.controller.api;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IndexControllerAPI.
 *
 * @author Evgeny_Ageev
 */
public interface IndexControllerAPI {

    @ApiOperation("Get root resource of Twilio service")
    @GetMapping()
    @ApiResponse(
            code = SC_OK,
            message = "Service Twilio is UP"
    )
    ResponseEntity<String> handleIndex();
}
