package com.smartsupport.controller.staff;

import com.smartsupport.dto.AdminTicketResponse;
import com.smartsupport.entity.ReplyType;
import com.smartsupport.entity.Ticket;
import com.smartsupport.entity.TicketReply;
import com.smartsupport.entity.User;
import com.smartsupport.service.StaffTicketService;
import com.smartsupport.service.TicketReplyService;
import com.smartsupport.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/staff/tickets")
@RequiredArgsConstructor
public class StaffTicketController {

    private final StaffTicketService staffTicketService;
    private final TicketReplyService replyService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAssignedTickets(Principal principal) {
        String email = principal.getName();
        User staff = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        List<AdminTicketResponse> tickets = staffTicketService.getAssignedTickets(staff.getId());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "tickets", tickets
        ));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(Principal principal) {
        String email = principal.getName();
        User staff = userRepository.findByEmail(email).orElseThrow();

        Map<String, Long> stats = staffTicketService.getTicketStats(staff.getId());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getTicket(@PathVariable String code, Principal principal) {
        String email = principal.getName();
        User staff = userRepository.findByEmail(email).orElseThrow();

        AdminTicketResponse ticket = staffTicketService.getTicketDetails(code, staff.getId());

        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{code}/replies")
    public ResponseEntity<?> getReplies(@PathVariable String code, Principal principal) {
        String email = principal.getName();
        User staff = userRepository.findByEmail(email).orElseThrow();

        Ticket ticket = staffTicketService.getTicketEntity(code);

        if (ticket.getStaff() == null || !ticket.getStaff().getId().equals(staff.getId())) {
            return ResponseEntity.status(403).body(Map.of(
                    "success", false,
                    "message", "Not allowed"
            ));
        }

        List<TicketReply> replies = replyService.getRepliesByTicket(ticket.getId());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "replies", replies
        ));
    }

    @PostMapping("/{code}/reply")
    public ResponseEntity<?> reply(@PathVariable String code,
                                   @RequestBody Map<String, String> body,
                                   Principal principal) {

        String msg = body.get("message");
        if (msg == null || msg.isBlank()) {
            return ResponseEntity.badRequest().body("Message cannot be empty");
        }

        String email = principal.getName();
        User staff = userRepository.findByEmail(email).orElseThrow();

        Ticket ticket = staffTicketService.getTicketEntity(code);

        TicketReply reply = replyService.addReply(ticket, staff, msg, ReplyType.STAFF);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "reply", reply
        ));
    }

    @PutMapping("/{code}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String code,
                                          @RequestBody Map<String, String> body,
                                          Principal principal) {

        String status = body.get("status");

        String email = principal.getName();
        User staff = userRepository.findByEmail(email).orElseThrow();

        AdminTicketResponse updated = staffTicketService.updateStatus(code, status, staff);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "ticket", updated
        ));
    }
}
