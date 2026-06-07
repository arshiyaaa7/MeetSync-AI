package com.hintro.meetingintelligence.repository;

import java.time.Instant;
import java.util.UUID;

import com.hintro.meetingintelligence.entity.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionItemRepository extends JpaRepository<ActionItem, UUID> {

    List<ActionItem> findByStatus(String status);
    List<ActionItem> findByAssignee(String assignee);
    List<ActionItem> findByMeetingId(UUID meetingId);
    List<ActionItem> findByStatusNotAndDueDateBefore(
            String status,
            Instant currentTime
    );
}