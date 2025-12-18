package com.smartsupport.controller.admin;

import com.smartsupport.dto.UserDto;
import com.smartsupport.service.AdminUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final AdminUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<UserDto> users = userService.getAllCustomers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        UserDto user = userService.getCustomerById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        UserDto updated = userService.toggleCustomerStatus(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        userService.deleteCustomer(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
