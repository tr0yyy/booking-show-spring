package com.fmi.bookingshow.repository;

import com.fmi.bookingshow.model.UserSpecificsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecificsRepository extends JpaRepository<UserSpecificsEntity, Long> {
}
