package com.example.myproj.repository.post;

import com.example.myproj.enums.PostState;
import com.example.myproj.model.post.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {

    PostLike findByPostIdAndUserId(Long postId, Long userId);

//    @Query(value = "SELECT pl.*\n" +
//            "FROM\n" +
//            "  (SELECT pl.id,\n" +
//            "         pl.post_id postId,\n" +
//            "         pl.user_id whoLiked,\n" +
//            "         u.first_name whoLikedName,\n" +
//            "         pl.created_at likeCreatedAt,\n" +
//            "         p.title,\n" +
//            "         p.thread_count threadCount,\n" +
//            "         p.swipe_right_count swipeRightCount,\n" +
//            "         t.id threadId,\n" +
//            "         t.created_at threadCreatedAt\n" +
//            "  FROM post_like pl\n" +
//            "     LEFT JOIN post p ON p.id = pl.post_id AND p.state = :state AND p.deleted_at IS NULL\n" +
//            "     LEFT JOIN thread t ON pl.post_id = t.post_id\n" +
//            "     LEFT JOIN users u ON u.id = :userId\n" +
//            "  WHERE pl.user_id = :userId AND pl.rate = 'LIKE'\n" +
//            "  UNION\n" +
//            "  SELECT pl.id,\n" +
//            "         pl.post_id postId,\n" +
//            "         pl.user_id whoLiked,\n" +
//            "         u.first_name whoLikedName,\n" +
//            "         pl.created_at likeCreatedAt,\n" +
//            "         p.title,\n" +
//            "         p.thread_count threadCount,\n" +
//            "         p.swipe_right_count swipeRightCount,\n" +
//            "         t.id threadId,\n" +
//            "         t.created_at threadCreatedAt\n" +
//            "  FROM post_like pl\n" +
//            "     LEFT JOIN post p ON p.id = pl.post_id AND p.state = :state\n" +
//            "       AND p.user_id = :userId AND p.deleted_at IS NULL\n" +
//            "     LEFT JOIN thread t ON pl.post_id = t.post_id\n" +
//            "     LEFT JOIN users u ON u.id = pl.user_id\n" +
//            "  WHERE pl.user_id != :userId AND pl.rate = 'LIKE') pl", nativeQuery = true)
//    Page<Pick> findPicks(@Param("userId") Long userId, @Param("state") PostState state, Pageable pageable);
//
//    @Query(value = "SELECT pl.*\n" +
//            "FROM\n" +
//            "  (SELECT pl.id,\n" +
//            "         pl.post_id postId,\n" +
//            "         pl.user_id whoLiked,\n" +
//            "         u.first_name whoLikedName,\n" +
//            "         pl.created_at likeCreatedAt,\n" +
//            "         p.title,\n" +
//            "         p.thread_count threadCount,\n" +
//            "         p.swipe_right_count swipeRightCount,\n" +
//            "         t.id threadId,\n" +
//            "         t.created_at threadCreatedAt\n" +
//            "  FROM post_like pl\n" +
//            "     LEFT JOIN post p ON p.id = pl.post_id AND p.id = :postId AND p.deleted_at IS NULL\n" +
//            "     LEFT JOIN thread t ON pl.post_id = t.post_id\n" +
//            "     LEFT JOIN users u ON u.id = :userId\n" +
//            "  WHERE pl.user_id = :userId AND pl.rate = 'LIKE'\n" +
//            "  UNION\n" +
//            "  SELECT pl.id,\n" +
//            "         pl.post_id postId,\n" +
//            "         pl.user_id whoLiked,\n" +
//            "         u.first_name whoLikedName,\n" +
//            "         pl.created_at likeCreatedAt,\n" +
//            "         p.title,\n" +
//            "         p.thread_count threadCount,\n" +
//            "         p.swipe_right_count swipeRightCount,\n" +
//            "         t.id threadId,\n" +
//            "         t.created_at threadCreatedAt\n" +
//            "  FROM post_like pl\n" +
//            "     LEFT JOIN post p ON p.id = pl.post_id AND p.id = :postId AND p.user_id = :userId AND p.deleted_at IS NULL\n" +
//            "     LEFT JOIN thread t ON pl.post_id = t.post_id\n" +
//            "     LEFT JOIN users u ON u.id = pl.user_id\n" +
//            "  WHERE pl.user_id != :userId AND pl.rate = 'LIKE') pl", nativeQuery = true)
//    Page<Pick> findPicksByPostId(@Param("userId") Long userId, @Param("postId") Long postId, Pageable pageable);
}
