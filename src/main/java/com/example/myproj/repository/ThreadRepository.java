package com.example.myproj.repository;

import com.example.myproj.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

    @Query("select t from Thread t where t.buyer.id = :userId or t.seller.id = :userId")
    Page<Thread> findAllByBuyerIdOrSellerId(@Param("userId") Long userId, Pageable pageable);

    List<Thread> findAllByBuyerId(Long buyerId);

    List<Thread> findAllBySellerId(Long sellerId);

    Optional<Thread> findByBuyerIdAndSellerIdAndPostId(Long buyerId, Long sellerId, Long postId);

    Optional<Thread> findBySid(String sid);
}
