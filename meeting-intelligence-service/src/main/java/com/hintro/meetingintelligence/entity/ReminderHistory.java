package com.hintro.meetingintelligence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reminder_history")
@Getter
@Setter
public class ReminderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "action_item_id")
    private ActionItem actionItem;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Instant sentAt = Instant.now();
}