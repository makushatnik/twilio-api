package com.example.myproj.service;

import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.enums.PostState;
import com.example.myproj.mapper.PickMapper;
import com.example.myproj.repository.PickViewRepository;
import com.example.myproj.repository.UserRepository;
import com.example.myproj.repository.post.TempLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.example.myproj.enums.PostRate.LIKE;
import static com.example.myproj.enums.PostState.ARCHIVED;

@Slf4j
@Service
@RequiredArgsConstructor
public class PickService {

    private final PickMapper mapper;
    private final PickViewRepository repository;
    private final TempLikeRepository tempLikeRepository;
    private final UserRepository userRepository;
    private final UrlResolver urlResolver;

    @Transactional(readOnly = true)
    public Page<PickDto> findAll(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return repository.findByWhoLikedIdOrOwnerId(userId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findAllOwn(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);
        return repository.findByOwnerId(userId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findOwnByState(Long userId, PostState state, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return repository.findByOwnerIdAndState(userId, state, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findOwnByPostId(Long userId, Long postId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return repository.findByOwnerIdAndPostId(userId, postId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findAllLiked(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);
        return repository.findByWhoLikedId(userId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findLikedByState(Long userId, PostState state, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return repository.findByWhoLikedIdAndState(userId, state, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findLikedByPostId(Long userId, Long postId, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);

        return repository.findByWhoLikedIdAndPostId(userId, postId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findArchivedPicksWon(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);
        return repository.findByReceivedUserIdAndState(userId, ARCHIVED, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findArchivedPicksLost(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return repository.findByReceivedUserIdNotAndState(userId, ARCHIVED, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PickDto> findTempPicks(String drive, Pageable pageable) {
        return tempLikeRepository.findAllByDriveAndRate(drive, LIKE, pageable)
                .map(mapper::toDtoFromTemp);
    }

    @Transactional
    public void refresh() {
        repository.refresh();
    }
}
