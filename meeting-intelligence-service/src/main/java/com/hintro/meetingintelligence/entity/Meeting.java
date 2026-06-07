package com.hintro.meetingintelligence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meetings")
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String transcript;

    @Column(columnDefinition = "TEXT")
    private String summaryJson;

    @Column(columnDefinition = "TEXT")
    private String decisionsJson;

    @Column(columnDefinition = "TEXT")
    private String followUpsJson;

    private Instant meetingDate;

    private Instant createdAt = Instant.now();

    @OneToMany(
            mappedBy = "meeting",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionItem> actionItems;
}