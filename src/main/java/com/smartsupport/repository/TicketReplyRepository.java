package com.smartsupport.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartsupport.entity.TicketReply;

@Repository
public interface TicketReplyRepository extends JpaRepository<TicketReply, Long> {

    List<TicketReply> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
