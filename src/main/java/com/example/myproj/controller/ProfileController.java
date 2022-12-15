package com.example.myproj.controller;

import static com.example.myproj.util.CommonUtils.sendErrors;
import static org.springframework.http.ResponseEntity.ok;

import com.example.myproj.controller.api.ProfileControllerAPI;
import com.example.myproj.dto.user.UserDto;
import com.example.myproj.dto.user.UserResponse;
import com.example.myproj.security.AuthenticatedUser;
import com.example.myproj.security.AuthenticationResolver;
import com.example.myproj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * ProfileController.
 *
 * @author Evgeny_Ageev
 */
@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerAPI {

    private final UserService service;
    private final AuthenticationResolver authenticationResolver;

    @Override
    public ResponseEntity<UserResponse> handleGet() {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(service.getCurrentUser(authenticatedUser));
    }

    @Override
    public ResponseEntity<UserResponse> handleUpdate(@RequestBody @Valid UserDto dto,
                                                     Errors errors) {
        sendErrors(errors);
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(service.update(authenticatedUser.getId(), dto));
    }

    @Override
    public ResponseEntity<Void> handleLogout(HttpSession session) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        session.removeAttribute(authenticatedUser.getUserName() + "-token");
        return ok().build();
    }
}
