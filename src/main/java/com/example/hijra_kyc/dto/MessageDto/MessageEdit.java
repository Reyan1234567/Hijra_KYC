package com.example.hijra_kyc.dto.MessageDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageEdit {
    @NotBlank(message="message is required")
    private String message;
}
