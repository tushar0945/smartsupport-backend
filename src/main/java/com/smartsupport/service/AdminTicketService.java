package com.smartsupport.service;

import com.smartsupport.dto.AdminTicketResponse;
import com.smartsupport.entity.*;
import com.smartsupport.repository.TicketRepository;
import com.smartsupport.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketReplyService replyService;

    public List<AdminTicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Map<String, Object> getTicketFullDetails(String code) {
        Ticket ticket = ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        List<TicketReply> replies = replyService.getRepliesByTicket(ticket.getId());

        return Map.of(
                "ticket", ticket,
                "replies", replies
        );
    }

    public Ticket assignStaff(String code, Long staffId) {

        Ticket ticket = ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        ticket.setStaff(staff);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        replyService.addSystemLog(
                ticket,
                "Ticket assigned to " + staff.getName(),
                ReplyType.ASSIGNED
        );

        return ticket;
    }

    public Ticket changeStatus(String code, String newStatus) {

        Ticket ticket = ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketStatus status = TicketStatus.valueOf(newStatus);
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        replyService.addSystemLog(
                ticket,
                "Status updated to: " + status.name(),
                ReplyType.STATUS_UPDATE
        );

        return ticket;
    }

    public List<TicketReply> getAllReplies(String code) {
        Ticket ticket = ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return replyService.getRepliesByTicket(ticket.getId());
    }

    private AdminTicketResponse mapToResponse(Ticket t) {
        return AdminTicketResponse.builder()
                .id(t.getId())
                .ticketCode(t.getTicketCode())
                .title(t.getTitle())
                .customerName(t.getCustomer() != null ? t.getCustomer().getName() : "-")
                .staffName(t.getStaff() != null ? t.getStaff().getName() : "Unassigned")
                .status(t.getStatus().name())
                .priority(t.getPriority())
                .category(t.getCategory())
                .createdAt(t.getCreatedAt().toString())
                .build();
    }
}
	