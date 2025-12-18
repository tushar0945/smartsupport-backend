package com.smartsupport.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartsupport.dto.TicketRequest;
import com.smartsupport.entity.Ticket;
import com.smartsupport.entity.TicketStatus;
import com.smartsupport.entity.User;
import com.smartsupport.repository.TicketRepository;
import com.smartsupport.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Ticket createTicket(TicketRequest req) {

        User customer = userRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Ticket ticket = Ticket.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .category(req.getCategory())
                .priority(req.getPriority())
                .customer(customer)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ticket = ticketRepository.save(ticket);

        String code = String.format("TCK-%03d", ticket.getId());
        ticket.setTicketCode(code);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByCustomer(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    public Ticket getTicketByCode(String code) {
        return ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
}
