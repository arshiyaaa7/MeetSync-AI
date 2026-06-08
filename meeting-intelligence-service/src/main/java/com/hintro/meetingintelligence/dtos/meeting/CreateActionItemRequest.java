package com.hintro.meetingintelligence.dtos.meeting;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class CreateActionItemRequest {

    @NotBlank
    private String task;

    @NotBlank
    private String assignee;

    private UUID meetingId;

    private Instant dueDate;

    private String status;
}