package com.example.myproj.controller;

import com.example.myproj.controller.api.PicksControllerAPI;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.enums.PostState;
import com.example.myproj.security.AuthenticatedUser;
import com.example.myproj.security.AuthenticationResolver;
import com.example.myproj.service.PickService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.myproj.util.PagedUtils.toPaged;
import static com.example.myproj.util.PostUtils.getPickDefaultSort;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class PicksController implements PicksControllerAPI {

    private final PickService pickService;
    private final AuthenticationResolver authenticationResolver;

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetWonPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findArchivedPicksWon(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetLostPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findArchivedPicksLost(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findAll(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetOwn(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findAllOwn(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetOwnByState(
            @PathVariable PostState state,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findOwnByState(authenticatedUser.getId(), state, pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetOwnByPostId(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findOwnByPostId(authenticatedUser.getId(), postId, pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetLiked(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findAllLiked(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetLikedByState(
            @RequestParam PostState state,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findLikedByState(authenticatedUser.getId(), state, pageable)));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetLikedByPostId(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPickDefaultSort());
        return ok(toPaged(pickService.findLikedByPostId(authenticatedUser.getId(), postId, pageable)));
    }
}
