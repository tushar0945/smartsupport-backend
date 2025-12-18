package com.smartsupport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartsupport.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findByCustomerId(Long customerId);
	Optional<Ticket> findByTicketCode(String ticketCode);
	List<Ticket> findByStaffId(Long staffId);

}
