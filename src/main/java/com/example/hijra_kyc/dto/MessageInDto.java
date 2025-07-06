package com.example.hijra_kyc.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class MessageInDto {
    private int sender;
    private String message;
    private int receiver;
}
