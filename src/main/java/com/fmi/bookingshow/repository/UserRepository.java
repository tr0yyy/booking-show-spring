package com.fmi.bookingshow.repository;

import com.fmi.bookingshow.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUserId(long userId);

    Optional<UserEntity> findTop1ByOrderByUserIdAsc();
}
