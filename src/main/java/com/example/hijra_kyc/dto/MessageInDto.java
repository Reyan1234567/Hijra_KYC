package com.example.hijra_kyc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Data
public class MessageInDto {
    @NotNull(message="senderId is required")
    private int sender;

    @NotBlank(message = "messageBody is required")
    private String message;

    @NotBlank(message="receiverId is required")
    private int receiver;
}
