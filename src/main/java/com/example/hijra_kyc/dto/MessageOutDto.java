package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class MessageOutDto {
    private Long id;
    private Long senderId;
    private Long recieverId;
    private String messageBody;
    private Instant sentTimestamp;
    private Instant recievedTimestamp;
    private int recieverStatus;
}
