package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class MessageOutDto {
    private int id;
    private int senderId;
    private int recieverId;
    private String messageBody;
    private Instant sentTimestamp;
    private Instant recievedTimestamp;
    private int recieverStatus;
}
