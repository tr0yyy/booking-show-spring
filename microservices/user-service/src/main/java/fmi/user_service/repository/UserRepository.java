package fmi.user_service.repository;

import fmi.user_service.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUserId(long userId);

    Optional<UserEntity> findTop1ByOrderByUserIdAsc();
}