package com.hintro.meetingintelligence.dtos.AI;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SummarizeRequest {

    @NotBlank
    private String transcript;
}