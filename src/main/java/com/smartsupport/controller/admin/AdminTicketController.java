package com.smartsupport.controller.admin;

import com.smartsupport.dto.AdminTicketResponse;
import com.smartsupport.dto.AssignStaffRequest;
import com.smartsupport.dto.ChangeStatusRequest;
import com.smartsupport.entity.TicketReply;
import com.smartsupport.service.AdminTicketService;
import com.smartsupport.service.TicketReplyService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/tickets")
@RequiredArgsConstructor
public class AdminTicketController {

    private final AdminTicketService ticketService;
    private final TicketReplyService replyService;

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        List<AdminTicketResponse> list = ticketService.getAllTickets();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getTicket(@PathVariable String code) {
        return ResponseEntity.ok(ticketService.getTicketFullDetails(code));
    }

    @PutMapping("/{code}/assign")
    public ResponseEntity<?> assignStaff(@PathVariable String code,
                                         @RequestBody AssignStaffRequest request) {
        return ResponseEntity.ok(ticketService.assignStaff(code, request.getStaffId()));
    }

    @PutMapping("/{code}/status")
    public ResponseEntity<?> changeStatus(@PathVariable String code,
                                          @RequestBody ChangeStatusRequest request) {
        return ResponseEntity.ok(ticketService.changeStatus(code, request.getStatus()));
    }

    @GetMapping("/{code}/replies")
    public ResponseEntity<?> getReplies(@PathVariable String code) {
        List<TicketReply> replies = ticketService.getAllReplies(code);
        return ResponseEntity.ok(replies);
    }
}
