package com.hintro.meetingintelligence.dtos.meeting;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranscriptEntryRequest {

    @NotBlank
    private String timestamp;

    @NotBlank
    private String speaker;

    @NotBlank
    private String text;
}