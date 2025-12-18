package com.smartsupport.controller.admin;

import com.smartsupport.dto.CreateStaffRequest;
import com.smartsupport.dto.UpdateStaffRequest;
import com.smartsupport.dto.StaffResponse;
import com.smartsupport.entity.Role;
import com.smartsupport.entity.Status;
import com.smartsupport.entity.User;
import com.smartsupport.service.AdminStaffService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/staff")
@RequiredArgsConstructor
public class AdminStaffController {

    private final AdminStaffService staffService;

    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody CreateStaffRequest req) {
        StaffResponse staff = staffService.createStaff(req);
        return ResponseEntity.ok(staff);
    }

    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        List<StaffResponse> list = staffService.getAllStaff();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        StaffResponse staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id,
                                         @RequestBody UpdateStaffRequest req) {
        StaffResponse staff = staffService.updateStaff(id, req);
        return ResponseEntity.ok(staff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted successfully");
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        StaffResponse staff = staffService.toggleStaffStatus(id);
        return ResponseEntity.ok(staff);
    }
}
