package com.furniture.repository;

import com.furniture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(User.Role role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // 统计新用户数量
    @Query("SELECT COUNT(u) FROM User u WHERE u.createTime >= :startDate")
    Long countNewUsers(@Param("startDate") LocalDateTime startDate);
}
