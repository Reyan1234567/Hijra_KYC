package com.example.hijra_kyc.dto.MessageDto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MessageDisplayDto {
    String sender;
    String content;
    Instant sentTimeStamp;
}
