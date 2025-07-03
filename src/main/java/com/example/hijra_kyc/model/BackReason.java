package com.example.hijra_kyc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "comment is required")
    private String comment;
    @NotBlank(message = "bank account is required")
    private String bankAccount;
    @NotNull(message = "commented by is required")
    @ManyToOne
    @JoinColumn(name = "commented_by", referencedColumnName = "id", nullable = false)
    private UserProfile commentedBy;
    
    private LocalDateTime commentedAt;
}
