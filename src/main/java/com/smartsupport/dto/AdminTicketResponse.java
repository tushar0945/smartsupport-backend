package com.smartsupport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminTicketResponse {

    private Long id;
    private String ticketCode;
    private String title;

    private String customerName;
    private String staffName;

    private String status;
    private String priority;
    private String category;

    private String createdAt;
}
