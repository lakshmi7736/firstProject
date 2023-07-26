package com.Ecom.Bee.Baa.Repository;

import com.Ecom.Bee.Baa.DTO.UserDto;
import com.Ecom.Bee.Baa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.accountNonLocked = false AND u.lockTime < :currentTime")
    List<User> findExpiredLockedUsers(@Param("currentTime") LocalDateTime currentTime);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
    void updateEnableStatus(@Param("id") Long id, @Param("enabled") boolean enabled);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedAttempt = :failedAttempt WHERE u.email = :email")
    void updateFailedAttempt(@Param("failedAttempt") int failedAttempt, @Param("email") String email);

//    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") String role);
}
