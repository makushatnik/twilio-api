package com.example.myproj.repository;

import com.example.myproj.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * ImageRepository.
 *
 * @author Evgeny_Ageev
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    Set<Image> findAllByPostId(Long postId);

    List<Image> findAllByIdIn(List<Long> ids);

    Optional<Image> findByPostIdAndFileName(Long postId, String fileName);
}
