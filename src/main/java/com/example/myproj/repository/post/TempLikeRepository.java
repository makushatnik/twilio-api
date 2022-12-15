package com.example.myproj.repository.post;

import com.example.myproj.enums.PostRate;
import com.example.myproj.model.post.TempLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for Unregistered likes.
 */
public interface TempLikeRepository extends CrudRepository<TempLike, Long> {

    List<TempLike> findAllByDrive(String drive);

    Page<TempLike> findAllByDriveAndRate(String drive, PostRate rate, Pageable pageable);

    @Query(value = "select t.id from TempLike t where t.post.id = :postId and t.drive = :drive")
    Long likeExists(@Param("postId") Long postId, @Param("drive") String drive);

}
