package com.example.hijra_kyc.dto;

import lombok.Data;

@Data
public class MessageEdit {
    private Long recieverId;
    private Long senderId;
    private String message;
}
