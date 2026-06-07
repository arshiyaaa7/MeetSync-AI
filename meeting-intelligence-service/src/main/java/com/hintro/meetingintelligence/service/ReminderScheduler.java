package com.hintro.meetingintelligence.service;

import com.hintro.meetingintelligence.entity.ActionItem;
import com.hintro.meetingintelligence.entity.ReminderHistory;
import com.hintro.meetingintelligence.repository.ActionItemRepository;
import com.hintro.meetingintelligence.repository.ReminderHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ActionItemRepository actionItemRepository;

    private final ReminderHistoryRepository reminderHistoryRepository;

    private final DiscordService discordService;

    @Scheduled(fixedRate = 60000)
    public void sendOverdueReminders() {

        try {

            log.info(
                    "Running overdue action item reminder scheduler"
            );

            List<ActionItem> overdueItems =
                    actionItemRepository
                            .findByStatusNotAndDueDateBefore(
                                    "COMPLETED",
                                    Instant.now()
                            );

            log.info(
                    "Found {} overdue action items",
                    overdueItems.size()
            );

            for (ActionItem actionItem : overdueItems) {

                log.info(
                        "Processing overdue action item with id: {}",
                        actionItem.getId()
                );

                boolean recentlySent =
                        reminderHistoryRepository
                                .existsByActionItemAndSentAtAfter(
                                        actionItem,
                                        Instant.now()
                                                .minus(1, ChronoUnit.HOURS)
                                );

                if (recentlySent) {

                    log.info(
                            "Reminder already sent recently for action item: {}",
                            actionItem.getId()
                    );

                    continue;
                }

                String message = """
                        🚨 Overdue Action Item
                        
                        Task: %s
                        Assigned To: %s
                        Due Date: %s
                        Status: %s
                        """
                        .formatted(
                                actionItem.getTask(),
                                actionItem.getAssignee(),
                                actionItem.getDueDate(),
                                actionItem.getStatus()
                        );

                log.info(
                        "Sending reminder for action item: {}",
                        actionItem.getId()
                );

                discordService.sendReminder(
                        message
                );

                ReminderHistory history =
                        new ReminderHistory();

                history.setActionItem(
                        actionItem
                );

                history.setMessage(
                        message
                );

                reminderHistoryRepository.save(
                        history
                );

                log.info(
                        "Reminder history persisted for action item: {}",
                        actionItem.getId()
                );
            }

            log.info(
                    "Reminder scheduler execution completed successfully"
            );

        } catch (Exception e) {

            log.error(
                    "Reminder scheduler execution failed",
                    e
            );
        }
    }
}