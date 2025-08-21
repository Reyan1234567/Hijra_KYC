package com.example.hijra_kyc.dto.UserProfileDto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListInterface {
    @NotNull
    private List<Long> ids;
}
