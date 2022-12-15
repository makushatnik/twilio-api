package com.example.myproj.service;

import com.example.myproj.dto.sms.RegisterDto;
import com.example.myproj.dto.sms.TokenDto;
import com.example.myproj.dto.user.UserDto;
import com.example.myproj.dto.user.UserResponse;
import com.example.myproj.exception.AlreadyRegisteredException;
import com.example.myproj.exception.UserNotRegisteredException;
import com.example.myproj.mapper.UserMapper;
import com.example.myproj.model.User;
import com.example.myproj.repository.UserRepository;
import com.example.myproj.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * UserService.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_FIRST_NAME_IS_NOT_SET = "User's First Name isn't set";

    private final UserMapper mapper;
    private final UserRepository repository;
    private final PostService postService;

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = repository.findById(id)
                              .orElseThrow(EntityNotFoundException::new);
        return mapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public UserResponse findByPhone(String phone) {
        User user = repository.findByPhoneNumber(phone)
                              .orElseThrow(EntityNotFoundException::new);
        return mapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public User getByPhone(String phone) {
        Optional<User> userOpt = repository.findByPhoneNumber(phone);
        return userOpt.orElse(null);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(AuthenticatedUser aUser) {
        User user = repository.findById(aUser.getId())
                              .orElseThrow(EntityNotFoundException::new);
        return mapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public TokenDto getTokenDto(String phone) {
        UserResponse userDto = findByPhone(phone);
        if (StringUtils.isEmpty(userDto.getFirstName())) {
            log.error(USER_FIRST_NAME_IS_NOT_SET);
            throw new UserNotRegisteredException(USER_FIRST_NAME_IS_NOT_SET);
        }
        return mapper.toTokenDto(userDto);
    }

    @Transactional
    public UserResponse update(Long id, UserDto dto) {
        User found = repository.findById(id)
                               .orElseThrow(EntityNotFoundException::new);

        found.setFirstName(dto.getFirstName())
                .setEmail(dto.getEmail())
                .setTwitter(dto.getTwitter())
                .setFacebook(dto.getFacebook());
        return mapper.toResponse(repository.save(found))
                     .setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public TokenDto register(RegisterDto registerDto) {
        String phone = registerDto.getPhone();
        User found = getByPhone(phone);
        if (!isNull(found)) {
            log.error("Trying to register 2nd time with phone {}", phone);
            throw new AlreadyRegisteredException("User had been registered already");
        }

        User user = new User()
                .setPhoneNumber(phone)
                .setFirstName(registerDto.getFirstName());
        user = repository.save(user);
        //remove all temporary likes/dislikes to a persistent tables
        postService.saveAllTemporaryLikes(user, registerDto.getDrive());
        return mapper.toTokenDto(mapper.toResponse(user));
    }

    @Transactional
    public void report(Long userId, String complaint) {
        User user = findById(userId);
        //TODO: save

        //TODO: sending an email with complaint
    }
}
