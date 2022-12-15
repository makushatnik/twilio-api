package com.example.myproj.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.example.myproj.controller.api.IndexControllerAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController.
 *
 * @author Evgeny_Ageev
 */
@RestController
public class IndexController implements IndexControllerAPI {

    private static final String SERVICE_IS_UP = "Service Twilio is UP";

    @Override
    public ResponseEntity<String> handleIndex() {
        return ok(SERVICE_IS_UP);
    }
}
