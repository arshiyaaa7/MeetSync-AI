package com.hintro.meetingintelligence.repository;

import com.hintro.meetingintelligence.entity.ActionItem;
import com.hintro.meetingintelligence.entity.ReminderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface ReminderHistoryRepository
        extends JpaRepository<ReminderHistory, UUID> {

    boolean existsByActionItemAndSentAtAfter(
            ActionItem actionItem,
            Instant time
    );
}