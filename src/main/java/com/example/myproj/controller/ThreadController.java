package com.example.myproj.controller;

import com.example.myproj.controller.api.ThreadControllerAPI;
import com.example.myproj.dto.chat.MessageRequest;
import com.example.myproj.dto.chat.MessageResponse;
import com.example.myproj.dto.chat.ThreadDto;
import com.example.myproj.dto.page.Paged;
import com.example.myproj.security.AuthenticatedUser;
import com.example.myproj.security.AuthenticationResolver;
import com.example.myproj.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.myproj.util.CommonUtils.*;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ThreadController implements ThreadControllerAPI {

    private final ThreadService service;
    private final AuthenticationResolver authenticationResolver;

    @Override
    public ResponseEntity<Paged<ThreadDto>> handleGetThreadList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size, getDefaultSort());
        return ok(service.getThreadList(authenticatedUser.getId(), pageable));
    }

    @Override
    public ResponseEntity<Paged<MessageResponse>> handleGetMessages(@PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ok(service.getMessages(id, page, size));
    }

    @Override
    public ResponseEntity<MessageResponse> handleSend(@RequestBody @Valid MessageRequest dto, Errors errors) {
        sendErrors(errors);
        AuthenticatedUser authenticatedUser = authenticationResolver.getAuthenticatedUser();
        return ok(service.send(dto, authenticatedUser.getId()));
    }

    @Override
    public ResponseEntity handleReport(@PathVariable Long userId, String complain) {
        checkIfLongIdIsPositive(userId);
        checkIfStringIsBlank(complain);
        service.report(userId, complain);
        return ok().build();
    }

    @Override
    public ResponseEntity<Void> handleDeleteById(@PathVariable Long id) {
        checkIfLongIdIsPositive(id);
        service.delete(id);
        return ok().build();
    }

    @Override
    public ResponseEntity<Void> handleDeleteBySid(@PathVariable String sid) {
        checkIfStringIsBlank(sid);
        service.delete(sid);
        return ok().build();
    }

    private Sort getDefaultSort() {
        return Sort.by(DESC, "createdAt");
    }
}
