package com.fmi.bookingshow.repository;

import com.fmi.bookingshow.model.TicketArchiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketArchiveRepository extends JpaRepository<TicketArchiveEntity, Long> {
}
