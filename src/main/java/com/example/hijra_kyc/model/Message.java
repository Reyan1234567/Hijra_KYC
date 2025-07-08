package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.catalina.User;

import java.time.Instant;

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
    private Integer id;

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