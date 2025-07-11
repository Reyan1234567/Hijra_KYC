package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kyc_images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "image_name", length = 100)
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_make_id")
    private MakeForm imageMake;

    @Size(max = 255)
    @Column(name = "image_description")
    private String imageDescription;
}