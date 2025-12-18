package com.smartsupport.controller;

import java.security.Principal;
import java.util.Map;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartsupport.dto.TicketRequest;
import com.smartsupport.entity.Ticket;
import com.smartsupport.entity.TicketReply;
import com.smartsupport.entity.ReplyType;
import com.smartsupport.entity.User;
import com.smartsupport.repository.UserRepository;
import com.smartsupport.service.TicketService;
import com.smartsupport.service.TicketReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketReplyService replyService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest req, Principal principal) {

        String email = principal.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        req.setCustomerId(customer.getId());

        Ticket ticket = ticketService.createTicket(req);

        replyService.addSystemLog(ticket, "Ticket created by customer", ReplyType.SYSTEM);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Ticket created successfully!",
                "ticketId", ticket.getId(),
                "ticketCode", ticket.getTicketCode()
        ));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<?> getMyTickets(Principal principal) {

        String email = principal.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        List<Ticket> tickets = ticketService.getTicketsByCustomer(customer.getId());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", tickets.size(),
                "tickets", tickets
        ));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getTicketByCode(@PathVariable String code, Principal principal) {

        String email = principal.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketService.getTicketByCode(code);

        if (!ticket.getCustomer().getId().equals(customer.getId())) {
            return ResponseEntity.status(403).body(Map.of(
                    "success", false,
                    "message", "You are not allowed to view this ticket"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "ticket", ticket
        ));
    }

    @PostMapping("/{code}/reply")
    public ResponseEntity<?> addReply(@PathVariable String code,
                                      @RequestBody Map<String, String> body,
                                      Principal principal) {

        String message = body.get("message");

        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Reply cannot be empty"
            ));
        }

        String email = principal.getName();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketService.getTicketByCode(code);

        if (!ticket.getCustomer().getId().equals(customer.getId())) {
            return ResponseEntity.status(403).body(Map.of(
                    "success", false,
                    "message", "You cannot reply to this ticket"
            ));
        }

        TicketReply reply = replyService.addReply(ticket, customer, message, ReplyType.CUSTOMER);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Reply added successfully",
                "reply", reply
        ));
    }

    @GetMapping("/{code}/replies")
    public ResponseEntity<?> getReplies(@PathVariable String code, Principal principal) {

        String email = principal.getName();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketService.getTicketByCode(code);

        if (!ticket.getCustomer().getId().equals(customer.getId())) {
            return ResponseEntity.status(403).body(Map.of(
                    "success", false,
                    "message", "Not allowed"
            ));
        }

        List<TicketReply> replies = replyService.getRepliesByTicket(ticket.getId());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", replies.size(),
                "replies", replies
        ));
    }
}
