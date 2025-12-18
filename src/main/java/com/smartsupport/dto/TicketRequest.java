package com.smartsupport.dto;

import lombok.Data;

@Data
public class TicketRequest {

    private String title;
    private String description;

    private String category; 
    private String priority; 

    private Long customerId;  
}
