package com.hintro.meetingintelligence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "action_items")
@Getter
@Setter
public class ActionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String task;

    private String assignee;

    private Instant dueDate;

    @Column(columnDefinition = "TEXT")
    private String citationsJson;

    /*
     * PENDING
     * IN_PROGRESS
     * COMPLETED
     * CANCELLED
     */
    private String status = "PENDING";

    private Instant createdAt = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}