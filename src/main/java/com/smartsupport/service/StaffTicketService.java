package com.smartsupport.service;

import com.smartsupport.dto.AdminTicketResponse;
import com.smartsupport.entity.*;
import com.smartsupport.repository.TicketRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffTicketService {

    private final TicketRepository ticketRepository;
    private final TicketReplyService replyService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<AdminTicketResponse> getAssignedTickets(Long staffId) {

        List<Ticket> tickets = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getStaff() != null && t.getStaff().getId().equals(staffId))
                .collect(Collectors.toList());

        return tickets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getTicketStats(Long staffId) {

        List<Ticket> tickets = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getStaff() != null && t.getStaff().getId().equals(staffId))
                .collect(Collectors.toList());

        long open = tickets.stream().filter(t -> t.getStatus() == TicketStatus.OPEN).count();
        long inProgress = tickets.stream().filter(t -> t.getStatus() == TicketStatus.IN_PROGRESS).count();
        long resolved = tickets.stream().filter(t -> t.getStatus() == TicketStatus.RESOLVED).count();
        long closed = tickets.stream().filter(t -> t.getStatus() == TicketStatus.CLOSED).count();

        return Map.of(
                "assigned", (long) tickets.size(),
                "open", open,
                "inProgress", inProgress,
                "resolved", resolved,
                "closed", closed
        );
    }

    public AdminTicketResponse getTicketDetails(String code, Long staffId) {

        Ticket ticket = getTicketEntity(code);

        if (ticket.getStaff() == null || !ticket.getStaff().getId().equals(staffId)) {
            throw new RuntimeException("Not allowed — ticket not assigned to you");
        }

        return toDto(ticket);
    }

    public Ticket getTicketEntity(String code) {
        return ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public AdminTicketResponse updateStatus(String code, String newStatus, User staff) {

        Ticket ticket = getTicketEntity(code);

        if (ticket.getStaff() == null || !ticket.getStaff().getId().equals(staff.getId())) {
            throw new RuntimeException("You cannot update this ticket");
        }

        TicketStatus statusEnum = TicketStatus.valueOf(newStatus.toUpperCase());

        ticket.setStatus(statusEnum);
        ticket.setUpdatedAt(java.time.LocalDateTime.now());

        ticketRepository.save(ticket);

        replyService.addSystemLog(
                ticket,
                "Status updated to: " + statusEnum.name(),
                ReplyType.STATUS_UPDATE
        );

        return toDto(ticket);
    }

    private AdminTicketResponse toDto(Ticket t) {
        return AdminTicketResponse.builder()
                .id(t.getId())
                .ticketCode(t.getTicketCode())
                .title(t.getTitle())
                .customerName(t.getCustomer().getName())
                .staffName(t.getStaff() != null ? t.getStaff().getName() : "Unassigned")
                .status(t.getStatus().name())
                .priority(t.getPriority())
                .category(t.getCategory())
                .createdAt(t.getCreatedAt().format(formatter))
                .build();
    }
}
