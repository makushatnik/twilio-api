package com.example.myproj.repository;

import com.example.myproj.enums.PostState;
import com.example.myproj.model.PickView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PickViewRepository extends JpaRepository<PickView, Long> {

    @Query("select p from PickView p where p.whoLikedId = :userId or p.ownerId = :userId")
    Page<PickView> findByWhoLikedIdOrOwnerId(@Param("userId") Long userId, Pageable pageable);

    Page<PickView> findByOwnerId(Long userId, Pageable pageable);

    Page<PickView> findByOwnerIdAndState(Long userId, PostState state, Pageable pageable);

    Page<PickView> findByOwnerIdAndPostId(Long userId, Long postId, Pageable pageable);

    Page<PickView> findByWhoLikedId(Long userId, Pageable pageable);

    Page<PickView> findByWhoLikedIdAndState(Long userId, PostState state, Pageable pageable);

    Page<PickView> findByWhoLikedIdAndPostId(Long userId, Long postId, Pageable pageable);

    Page<PickView> findByReceivedUserIdAndState(Long userId, PostState state, Pageable pageable);

    Page<PickView> findByReceivedUserIdNotAndState(Long userId, PostState state, Pageable pageable);

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW pick_view", nativeQuery = true)
    void refresh();
}
