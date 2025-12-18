package com.smartsupport.service;

import com.smartsupport.dto.CreateStaffRequest;
import com.smartsupport.dto.UpdateStaffRequest;
import com.smartsupport.dto.StaffResponse;
import com.smartsupport.entity.Role;
import com.smartsupport.entity.Status;
import com.smartsupport.entity.User;
import com.smartsupport.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminStaffService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public StaffResponse createStaff(CreateStaffRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User staff = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.STAFF)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(staff);

        return mapToResponse(staff);
    }


    public List<StaffResponse> getAllStaff() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.STAFF)
                .map(this::mapToResponse)
                .toList();
    }


    public StaffResponse getStaffById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (user.getRole() != Role.STAFF)
            throw new RuntimeException("User is not staff!");

        return mapToResponse(user);
    }

   
    public StaffResponse updateStaff(Long id, UpdateStaffRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setRole(Role.valueOf(req.getRole()));
        user.setStatus(Status.valueOf(req.getStatus()));

        userRepository.save(user);
        return mapToResponse(user);
    }


    public void deleteStaff(Long id) {
        userRepository.deleteById(id);
    }


    public StaffResponse toggleStaffStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (user.getStatus() == Status.ACTIVE)
            user.setStatus(Status.INACTIVE);
        else
            user.setStatus(Status.ACTIVE);

        userRepository.save(user);
        return mapToResponse(user);
    }

    private StaffResponse mapToResponse(User user) {
        return StaffResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }
}
