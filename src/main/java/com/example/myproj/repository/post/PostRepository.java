package com.example.myproj.repository.post;

import com.example.myproj.enums.PostState;
import com.example.myproj.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * PostRepository.
 *
 * @author Evgeny_Ageev
 */
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findAllByStateEquals(PostState state, Pageable pageable);

    Page<Post> findOwnByUserIdAndState(Long userId, PostState state, Pageable pageable);

    Optional<Post> findByIdAndStateEquals(Long id, PostState state);

    @Query("select p from Post p " +
            "where p.createdAt = p.renewAt and p.state = 'ACTIVE' and p.renewAt <= :date"
    )
    List<Post> findNewExpired(LocalDateTime date);

    @Query("select p from Post p " +
            "where p.createdAt <> p.renewAt and p.state = 'ACTIVE' and p.renewAt <= :date")
    List<Post> findExpired(LocalDateTime date);

    @Query("select p from Post p where p.state = 'ACTIVE' and p.id not in(" +
                "select pd.post.id from PostLike pd where pd.user.id = :userId and pd.rate = 'DISLIKE')")
    Page<Post> findAllExceptDisliked(@Param("userId") Long userId, Pageable pageable);

    @Query("select p from Post p where p.state = 'ARCHIVED' and p.claimedUser.id = :userId and p.id in(" +
                "select pd.post.id from PostLike pd where pd.user.id = :userId and pd.rate = 'LIKE')")
    Page<Post> findArchivedPostsWon(@Param("userId") Long userId, Pageable pageable);

    @Query("select p from Post p where p.state = 'ARCHIVED' and p.claimedUser.id <> :userId and p.id in(" +
            "select pd.post.id from PostLike pd where pd.user.id = :userId and pd.rate = 'LIKE')")
    Page<Post> findArchivedPostsLost(@Param("userId") Long userId, Pageable pageable);
}
