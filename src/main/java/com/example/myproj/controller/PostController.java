package com.example.myproj.controller;

import static com.example.myproj.enums.PostState.ACTIVE;
import static com.example.myproj.util.CommonUtils.*;
import static com.example.myproj.util.PagedUtils.toPaged;
import static com.example.myproj.util.PostUtils.getPostDefaultSort;
import static org.springframework.http.ResponseEntity.ok;

import com.example.myproj.controller.api.PostControllerAPI;
import com.example.myproj.dto.FilterDto;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.post.PostRequest;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.enums.Category;
import com.example.myproj.enums.PostRate;
import com.example.myproj.enums.PostState;
import com.example.myproj.security.AuthenticatedUser;
import com.example.myproj.security.AuthenticationResolver;
import com.example.myproj.service.PostService;
import com.example.myproj.service.search.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * PostController.
 *
 * @author Evgeny_Ageev
 */
@RestController
@RequiredArgsConstructor
public class PostController implements PostControllerAPI {

    private final PostService postService;
    private final PostSearchService searchService;
    private final AuthenticationResolver authenticationResolver;

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetPage(@RequestBody FilterDto dto) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        dto.setState(ACTIVE);
        Page<PostResponse> result = searchService.find(dto, authenticatedUser.getId());
        return ok(toPaged(result));
    }

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetActivePage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findAllActive(pageable)));
    }

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetOwnPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam PostState state) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findOwn(authenticatedUser.getId(), state, pageable)));
    }

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetWonPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findArchivedPostsWon(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetLostPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findArchivedPostsLost(authenticatedUser.getId(), pageable)));
    }

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetArchivedPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findAllArchived(pageRequest)));
    }

    @Override
    public ResponseEntity<PostResponse> handleGet(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        return ok(postService.getById(id));
    }

//    @Override
//    public ResponseEntity<PostResponse> handleCreate(@RequestBody @Valid PostRequest dto,
//                                                     Errors errors) {
//        sendErrors(errors);
//        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
//        return ok(postService.create(dto, authenticatedUser.getId()));
//    }

    @Override
    public ResponseEntity<PostResponse> handleCreate(@RequestBody @Valid PostRequest dto,
                                                     Errors errors) {
        checkIfListIsEmpty(dto.getImageIds());
        sendErrors(errors);
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(postService.createWithImages(dto, authenticatedUser.getId()));
    }

    @Override
    public ResponseEntity<PostResponse> handleAttach(@PathVariable Long id,
                                                     @RequestParam("imageIds") List<Long> imageIds) {
        checkIfLongIdIsPositive(id);
        checkIfListIsEmpty(imageIds);
        return ok(postService.attach(id, imageIds));
    }

//    @Override
//    public ResponseEntity<PostResponse> handleCreateWithFiles(@ModelAttribute PostRequest dto) {
//        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
//        return ok(postService.createWithFiles(dto, authenticatedUser.getId()));
//    }

    @Override
    public ResponseEntity<PostResponse> handleUpdate(@PathVariable Long id,
                                                     @RequestBody @Valid PostRequest dto,
                                                     Errors errors) {
        sendErrors(errors);
        return ok(postService.update(id, dto));
    }

    @Override
    public ResponseEntity<PostResponse> handleRenew(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        return ok(postService.renew(id));
    }

    @Override
    public ResponseEntity<PostResponse> handleGiveAway(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(postService.markGivenAway(id, authenticatedUser.getId()));
    }

    @Override
    public ResponseEntity<PostResponse> handleReport(@PathVariable Long id, String complaint) {
        checkIfLongIdIsPositive(id);
        checkIfStringIsBlank(complaint);
        return ok(postService.report(id, complaint));
    }

    @Override
    public ResponseEntity<PostResponse> handleLike(@PathVariable Long id, PostRate rate) {
        checkIfLongIdIsPositive(id);
        checkIfValueIsNotNull(rate);
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(postService.like(id, rate, authenticatedUser));
    }

    @Override
    public ResponseEntity<PostResponse> handleShare(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        return ok(postService.share(id));
    }

    @Override
    public ResponseEntity<Void> handleDelete(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        postService.delete(id);
        return ok().build();
    }

    @Override
    public ResponseEntity<Set<Category>> handleGetCategories() {
        return ok(postService.getCategories());
    }
}
