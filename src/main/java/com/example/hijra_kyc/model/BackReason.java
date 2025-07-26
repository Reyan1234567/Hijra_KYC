package com.example.hijra_kyc.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
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
    private Long id;
    @NotBlank(message = "comment is required")
    private String comment;
    @OneToOne
    @JoinColumn(referencedColumnName="id")
    @NotNull
    private MakeForm makeId;

    @NotNull
    private LocalDateTime rejectionTime;
}
