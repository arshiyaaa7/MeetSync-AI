package com.hintro.meetingintelligence.service;

import com.hintro.meetingintelligence.dtos.meeting.CreateActionItemRequest;
import com.hintro.meetingintelligence.dtos.meeting.UpdateActionItemStatusRequest;
import com.hintro.meetingintelligence.entity.ActionItem;
import com.hintro.meetingintelligence.entity.Meeting;
import com.hintro.meetingintelligence.exception.ResourceNotFoundException;
import com.hintro.meetingintelligence.repository.ActionItemRepository;
import com.hintro.meetingintelligence.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionItemService {

    private final ActionItemRepository actionItemRepository;

    private final MeetingRepository meetingRepository;

    public ActionItem create(
            CreateActionItemRequest request
    ) {

        log.info(
                "Creating action item for assignee: {}",
                request.getAssignee()
        );

        ActionItem actionItem =
                new ActionItem();

        actionItem.setTask(
                request.getTask()
        );

        actionItem.setDueDate(
                request.getDueDate()
        );

        actionItem.setAssignee(
                request.getAssignee()
        );

        if (request.getMeetingId() != null) {

            log.info(
                    "Fetching meeting with id: {}",
                    request.getMeetingId()
            );

            Meeting meeting =
                    meetingRepository.findById(
                            request.getMeetingId()
                    ).orElseThrow(() -> {

                        log.error(
                                "Meeting not found with id: {}",
                                request.getMeetingId()
                        );

                        return new ResourceNotFoundException(
                                "Meeting not found"
                        );
                    });

            actionItem.setMeeting(meeting);
        }

        ActionItem savedActionItem =
                actionItemRepository.save(
                        actionItem
                );

        log.info(
                "Action item created successfully with id: {}",
                savedActionItem.getId()
        );

        return savedActionItem;
    }

    public ActionItem updateStatus(
            UUID id,
            UpdateActionItemStatusRequest request
    ) {

        log.info(
                "Updating status for action item: {}",
                id
        );

        ActionItem actionItem =
                actionItemRepository.findById(id)
                        .orElseThrow(() -> {

                            log.error(
                                    "Action item not found with id: {}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "Action item not found"
                            );
                        });

        actionItem.setStatus(
                request.getStatus()
        );

        ActionItem updatedActionItem =
                actionItemRepository.save(
                        actionItem
                );

        log.info(
                "Action item status updated to: {}",
                request.getStatus()
        );

        return updatedActionItem;
    }

    public List<ActionItem> getAll(
            String status,
            String assignee,
            UUID meetingId
    ) {

        log.info(
                "Fetching action items with filters status={}, assignee={}, meetingId={}",
                status,
                assignee,
                meetingId
        );

        if (status != null) {
            return actionItemRepository.findByStatus(status);
        }

        if (assignee != null) {
            return actionItemRepository.findByAssignee(assignee);
        }

        if (meetingId != null) {
            return actionItemRepository.findByMeetingId(meetingId);
        }

        return actionItemRepository.findAll();
    }

    public List<ActionItem> getOverdueActionItems() {

        log.info("Fetching overdue action items");

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

        return overdueItems;
    }
}