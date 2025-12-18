package com.smartsupport.dto;

import lombok.Data;

@Data
public class UpdateStaffRequest {
    private String name;
    private String email;
    private String phone;
    private String role;     
    private String status;   
}
