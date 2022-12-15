package com.example.myproj.service;

import static com.example.myproj.enums.PostRate.LIKE;
import static com.example.myproj.enums.PostState.ACTIVE;
import static com.example.myproj.enums.PostState.ARCHIVED;
import static com.example.myproj.util.PostUtils.sorted;
import static java.util.Objects.isNull;

import com.example.myproj.dto.post.PostRequest;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.dto.post.TempLikeDto;
import com.example.myproj.enums.Category;
import com.example.myproj.enums.Condition;
import com.example.myproj.enums.PostRate;
import com.example.myproj.enums.PostState;
import com.example.myproj.mapper.PostMapper;
import com.example.myproj.model.Image;
import com.example.myproj.model.Location;
import com.example.myproj.model.User;
import com.example.myproj.model.post.*;
import com.example.myproj.repository.post.PostRepository;
import com.example.myproj.repository.UserRepository;
import com.example.myproj.repository.post.PostLikeRepository;
import com.example.myproj.repository.post.TempLikeRepository;
import com.example.myproj.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PostService.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String CLAIM_OWN_POST_ERROR = "You can't claim your own post";
    private static final String LIKE_OWN_POST_ERROR = "You can't like your own post";

    private final PostMapper mapper;
    private final PostRepository repository;
    private final PostLikeRepository postLikeRepository;
    private final TempLikeRepository tempLikeRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final LocationService locationService;

    @Transactional(readOnly = true)
    public Page<PostResponse> findAllActive(Pageable pageable) {
        return repository.findAllByStateEquals(ACTIVE, pageable)
                         .map(post -> sorted(mapper.toResponse(post)));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAllArchived(Pageable pageable) {
        return repository.findAllByStateEquals(ARCHIVED, pageable)
                         .map(post -> sorted(mapper.toResponse(post)));
    }

    /**
     * Find own posts for a seller by his user id.
     *
     * @param userId    Long
     * @param pageable  Pageable
     * @return Page
     */
    @Transactional
    public Page<PostResponse> findOwn(Long userId, PostState state, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);

        return repository.findOwnByUserIdAndState(userId, state, pageable)
                         .map(post -> sorted(mapper.toResponse(post)));
    }

    /**
     * Find all posts a buyer won by his user id.
     *
     * @param userId    Long
     * @param pageable  Pageable
     * @return  Page
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> findArchivedPostsWon(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                      .orElseThrow(EntityNotFoundException::new);

        return repository.findArchivedPostsWon(userId, pageable)
                .map(post -> sorted(mapper.toResponse(post)));
    }

    /**
     * Find all posts a buyer won by his user id.
     *
     * @param userId    Long
     * @param pageable  Pageable
     * @return  Page
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> findArchivedPostsLost(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        return repository.findArchivedPostsLost(userId, pageable)
                .map(post -> sorted(mapper.toResponse(post)));
    }

    @Transactional
    public PostResponse getById(Long id) {
        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);
        PostResponse dto = sorted(mapper.toResponse(found));
        int count = isNull(found.getViewCount()) ? 0 : found.getViewCount();
        found.setViewCount(++count);
        repository.save(found);
        return dto;
    }

    @Transactional
    public Post findById(Long id) {
        return repository.findByIdAndStateEquals(id, ACTIVE)
                         .orElseThrow(EntityNotFoundException::new);
    }

    public Set<Category> getCategories() {
        return EnumSet.allOf(Category.class);
    }

    @Transactional
    public PostResponse create(PostRequest dto, Long userId) {
        return sorted(mapper.toResponse(createInner(dto, userId, null)));
    }

    @Transactional
    public PostResponse createWithImages(PostRequest dto, Long userId) {
        List<Image> images = getAndCheckImagesCount(dto.getImageIds());

        Post post = createInner(dto, userId, images);
        return sorted(removeImagesAndGetDto(post, images));
    }

    @Transactional
    public void save(Post post) {
        repository.save(post);
    }

    @Transactional
    public PostResponse attach(Long id, List<Long> imageIds) {
        List<Image> images = getAndCheckImagesCount(imageIds);

        Post post = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return sorted(removeImagesAndGetDto(post, images));
    }

//    @Transactional
//    public PostResponse createWithFiles(PostRequest dto, Long userId) {
//        if (dto.getFiles().length == 0) {
//            throw new IllegalArgumentException("There are no pictures at all");
//        }
//
//        Post post = createInner(dto, userId);
//        PostResponse postDto = mapper.toResponse(post);
//        postDto.setImages(imageService.multiUploadFromPost(post, dto.getFiles()));
//        return postDto;
//    }

    @Transactional
    public PostResponse renew(Long id) {
        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);
        found.setRenewAt(LocalDateTime.now());
        return sorted(mapper.toResponse(repository.save(found)))
                     .setUpdatedAt(LocalDateTime.now());//WA
    }

    @Transactional
    public PostResponse markGivenAway(Long id, Long userId) {
        User givenUser = userRepository.findById(userId)
                                       .orElseThrow(EntityNotFoundException::new);

        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);
        if (found.getUser().getId().equals(userId)) {
            log.error(CLAIM_OWN_POST_ERROR);
            throw new AccessDeniedException(CLAIM_OWN_POST_ERROR);
        }

        found.setState(ARCHIVED);
        found.setClaimedUser(givenUser);
        return sorted(mapper.toResponse(repository.save(found)))
                     .setUpdatedAt(LocalDateTime.now());//WA
    }

    @Transactional
    public PostResponse report(Long id, String complaint) {
        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);
        int count = isNull(found.getReportCount()) ? 0 : found.getReportCount();
        found.setReportCount(++count);
        //TODO: send email
        return sorted(mapper.toResponse(repository.save(found)))
                     .setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public PostResponse like(Long id, PostRate rate, AuthenticatedUser aUser) {
        User user = userRepository.findById(aUser.getId())
                                  .orElseThrow(EntityNotFoundException::new);

        Post foundPost = repository.findByIdAndStateEquals(id, ACTIVE)
                                   .orElseThrow(EntityNotFoundException::new);
        if (foundPost.getUser().getId().equals(user.getId())) {
            log.error(LIKE_OWN_POST_ERROR);
            throw new AccessDeniedException(LIKE_OWN_POST_ERROR);
        }

        updateCounts(foundPost, user, rate);
        return sorted(mapper.toResponse(repository.save(foundPost)))
                     .setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void likeUnregistered(TempLikeDto dto) {
        Post post = repository.findByIdAndStateEquals(dto.getPostId(), ACTIVE)
                              .orElseThrow(EntityNotFoundException::new);

        if (isNull(tempLikeRepository.likeExists(dto.getPostId(), dto.getDrive()))) {
            tempLikeRepository.save(new TempLike()
                    .setPost(post)
                    .setDrive(dto.getDrive())
                    .setRate(dto.getRate()));
        } else {
            log.error("User with drive {} tried to like post with id {} again", dto.getDrive(), dto.getPostId());
            throw new IllegalArgumentException("Trying to like post with id " + dto.getPostId() + " again");
        }
    }

    @Transactional
    public void saveAllTemporaryLikes(User user, String drive) {
        List<TempLike> tempLikes = getAllTemporaryLikes(drive);
        List<PostLike> likes = tempLikes.stream()
                .map(like -> new PostLike()
                        .setUser(user)
                        .setPost(like.getPost())
                        .setRate(like.getRate()))
                .collect(Collectors.toList());
        postLikeRepository.saveAll(likes);
        tempLikeRepository.deleteAll(tempLikes);
    }

    @Transactional
    public PostResponse share(Long id) {
        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);
        int count = isNull(found.getShareCount()) ? 0 : found.getShareCount();
        found.setShareCount(++count);
        return sorted(mapper.toResponse(repository.save(found)))
                     .setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public PostResponse update(Long id, PostRequest dto) {
        Post found = repository.findByIdAndStateEquals(id, ACTIVE)
                               .orElseThrow(EntityNotFoundException::new);

        Location location = locationService.findOrCreate(dto.getLocation());
        found.setCondition(Condition.valueOf(dto.getCondition().getName()))
             .setTitle(dto.getTitle())
             .setDescription(dto.getDescription())
             .setCategory(dto.getStuffCategory())
             .setLocation(location);
        return sorted(mapper.toResponse(repository.save(found)))
                     .setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void delete(Long id) {
        Post found = repository.findById(id)
                               .orElseThrow(EntityNotFoundException::new);

        found.setDeletedAt(LocalDateTime.now());
        repository.save(found);
    }

    private PostResponse removeImagesAndGetDto(Post post, List<Image> images) {
        imageService.removeImages(post, images);
        return sorted(mapper.toResponse(post));
    }

    private List<Image> getAndCheckImagesCount(List<Long> imageIds) {
        List<Image> images = imageService.findAllByIdList(imageIds);
        int dtoImageListSize = imageIds.size();
        if (images.size() != dtoImageListSize) {
            log.error("Some images were not found. repo size - {}, dto size - {}", images.size(), dtoImageListSize);
            throw new IllegalStateException("Some images were not found");
        }
        return images;
    }

    private List<TempLike> getAllTemporaryLikes(String drive) {
        return tempLikeRepository.findAllByDrive(drive);
    }

    private Post createInner(PostRequest dto, Long userId, List<Image> images) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(EntityNotFoundException::new);

        Location location = locationService.findOrCreate(dto.getLocation());
        return repository.save(mapper.toModel(dto)
                .setUser(user)
                .setState(ACTIVE)
                .setImages(images)
                .setRenewAt(LocalDateTime.now())
                .setLocation(location));
    }

    private void updateCounts(Post post, User user, PostRate rate) {
        long countLike = isNull(post.getSwipeRightCount()) ? 0 : post.getSwipeRightCount();
        long countDisLike = isNull(post.getSwipeLeftCount()) ? 0 : post.getSwipeLeftCount();

        if (addLike(post, user, rate)) {
            if (rate == LIKE) {
                post.setSwipeRightCount(++countLike);
            } else {
                post.setSwipeLeftCount(++countDisLike);
            }
        }
    }

    private boolean addLike(Post post, User user, PostRate rate) {
        PostLike like = postLikeRepository.findByPostIdAndUserId(post.getId(), user.getId());
        if (isNull(like)) {
            postLikeRepository.save(new PostLike()
                    .setPost(post)
                    .setUser(user)
                    .setRate(rate));
            return true;
        } else if (!like.getRate().equals(rate)) {
            log.error("{} tried to make another rate (was {}) to post with id {}",
                    user.getId(), rate, post.getId());
            throw new IllegalArgumentException("You already setted " + like.getRate() + " to that post");
        }
        return false;
    }

    /**
     * Archive expired posts.
     *
     * At first we find all not raised in the feed and check if 5 days gone already
     * and set ARCHIVE in that case.
     * After that we find all already raised in the feed and check if 3 days gone
     * and set ARCHIVE too.
     */
    @Transactional
    public void archiveExpired() {
        LocalDateTime now = LocalDateTime.now();

        List<Post> list = repository.findNewExpired(now.minusDays(5));
        list.forEach(post -> post.setState(ARCHIVED));
        repository.saveAll(list);

        list = repository.findExpired(now.minusDays(3));
        list.forEach(post -> post.setState(ARCHIVED));
        repository.saveAll(list);
    }
}
