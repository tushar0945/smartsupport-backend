package com.smartsupport.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartsupport.entity.ReplyType;
import com.smartsupport.entity.Ticket;
import com.smartsupport.entity.TicketReply;
import com.smartsupport.entity.User;
import com.smartsupport.repository.TicketReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketReplyService {

    private final TicketReplyRepository replyRepository;

    public TicketReply addReply(Ticket ticket, User user, String message, ReplyType type) {

        TicketReply reply = TicketReply.builder()
                .ticket(ticket)
                .user(user)
                .type(type)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        return replyRepository.save(reply);
    }

    public TicketReply addSystemLog(Ticket ticket, String message, ReplyType type) {

        TicketReply reply = TicketReply.builder()
                .ticket(ticket)
                .user(null)
                .type(type)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        return replyRepository.save(reply);
    }

    public List<TicketReply> getRepliesByTicket(Long ticketId) {
        return replyRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}
