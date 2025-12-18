package com.smartsupport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String status;
}
