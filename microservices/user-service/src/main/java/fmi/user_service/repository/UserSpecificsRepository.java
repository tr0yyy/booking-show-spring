package fmi.user_service.repository;

import fmi.user_service.model.UserSpecificsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecificsRepository extends JpaRepository<UserSpecificsEntity, Long> {
}
