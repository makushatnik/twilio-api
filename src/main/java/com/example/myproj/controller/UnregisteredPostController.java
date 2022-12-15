package com.example.myproj.controller;

import com.example.myproj.controller.api.UnregisteredPostControllerAPI;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.dto.post.TempLikeDto;
import com.example.myproj.service.PickService;
import com.example.myproj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.myproj.util.CommonUtils.*;
import static com.example.myproj.util.PagedUtils.toPaged;
import static com.example.myproj.util.PostUtils.getPostDefaultSort;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class UnregisteredPostController implements UnregisteredPostControllerAPI {

    private final PostService postService;
    private final PickService pickService;

    @Override
    public ResponseEntity<Paged<PostResponse>> handleGetActivePage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(page, size, getPostDefaultSort());
        return ok(toPaged(postService.findAllActive(pageable)));
    }

    @Override
    public ResponseEntity<PostResponse> handleGet(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        return ok(postService.getById(id));
    }

    @Override
    public ResponseEntity<PostResponse> handleReport(@PathVariable Long id, String complaint) {
        checkIfLongIdIsPositive(id);
        checkIfStringIsBlank(complaint);
        return ok(postService.report(id, complaint));
    }

    @Override
    public ResponseEntity<Void> handleLike(@RequestBody @Valid TempLikeDto dto,
                                           Errors errors) {
        sendErrors(errors);
        postService.likeUnregistered(dto);
        return ok().build();
    }

    @Override
    public ResponseEntity<PostResponse> handleShare(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        return ok(postService.share(id));
    }

    @Override
    public ResponseEntity<Paged<PickDto>> handleGetTempLikes(@RequestParam String drive,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        checkIfStringIsBlank(drive);
        PageRequest pageable = PageRequest.of(page, size);
        return ok(toPaged(pickService.findTempPicks(drive, pageable)));
    }
}
