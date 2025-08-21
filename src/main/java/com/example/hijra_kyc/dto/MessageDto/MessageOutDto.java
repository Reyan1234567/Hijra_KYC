package com.example.hijra_kyc.dto.MessageDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class MessageOutDto {
    @JsonIgnore
    private Long id;
    private Long senderId;
    private Long recieverId;
    private String messageBody;
    @JsonIgnore
    private Instant sentTimestamp;
    @JsonIgnore
    private Instant recievedTimestamp;
    private int recieverStatus;
}
