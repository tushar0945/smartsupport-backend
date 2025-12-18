package com.smartsupport.service;

import com.smartsupport.dto.AuthResponse;
import com.smartsupport.dto.LoginRequest;
import com.smartsupport.dto.RegisterRequest;
import com.smartsupport.dto.UserDto;
import com.smartsupport.entity.Role;
import com.smartsupport.entity.Status;
import com.smartsupport.entity.User;
import com.smartsupport.repository.UserRepository;
import com.smartsupport.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Email already registered!")
                    .data(null)
                    .build();
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", userDto);
        responseData.put("token", token);

        return AuthResponse.builder()
                .success(true)
                .message("Registration successful!")
                .data(responseData)
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return AuthResponse.builder()
                    .success(false)
                    .message("User not found!")
                    .data(null)
                    .build();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Invalid password!")
                    .data(null)
                    .build();
        }

        String token = jwtUtil.generateToken(user.getEmail());

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", userDto);
        responseData.put("token", token);

        return AuthResponse.builder()
                .success(true)
                .message("Login successful!")
                .data(responseData)
                .build();
    }
}
