package com.example.myproj.repository;

import com.example.myproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * UserRepository.
 *
 * @author Evgeny_Ageev
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByTwitter(String twitter);

    Optional<User> findByFacebook(String facebook);

//    @Query(value = "SELECT t.id FROM temp_likes t WHERE t.drive = :drive", nativeQuery = true)
//    Optional<Long> findAnonimousByDrive(@Param("drive") String drive);
}
