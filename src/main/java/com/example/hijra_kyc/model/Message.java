package com.example.hijra_kyc.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kyc_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserProfile senderId;

    @ManyToOne
    @JoinColumn(name = "reciever_id", referencedColumnName = "id")
    private UserProfile recieverId;

    @Size(max = 1000)
    @Column(name = "message_body")
    private String messageBody;

    @Column(name = "sent_timestamp")
    private Instant sentTimestamp;

    @Column(name = "received_timestamp")
    private Instant receivedTimestamp;

    @Column(name = "reciever_status")
    private int recieverStatus;

}