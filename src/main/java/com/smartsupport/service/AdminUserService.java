package com.smartsupport.service;

import com.smartsupport.dto.UserDto;
import com.smartsupport.entity.Role;
import com.smartsupport.entity.Status;
import com.smartsupport.entity.User;
import com.smartsupport.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllCustomers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .map(this::mapToDto)
                .toList();
    }

    public UserDto getCustomerById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (user.getRole() != Role.CUSTOMER)
            throw new RuntimeException("User is not a customer");

        return mapToDto(user);
    }

    public UserDto toggleCustomerStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (user.getStatus() == Status.ACTIVE) {
            user.setStatus(Status.INACTIVE);
        } else {
            user.setStatus(Status.ACTIVE);
        }

        userRepository.save(user);
        return mapToDto(user);
    }

    public void deleteCustomer(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }
}
