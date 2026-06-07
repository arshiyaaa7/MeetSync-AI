package com.hintro.meetingintelligence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hintro.meetingintelligence.dtos.meeting.CreateMeetingRequest;
import com.hintro.meetingintelligence.dtos.meeting.MeetingResponse;
import com.hintro.meetingintelligence.entity.ActionItem;
import com.hintro.meetingintelligence.entity.Meeting;
import com.hintro.meetingintelligence.repository.ActionItemRepository;
import com.hintro.meetingintelligence.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final ActionItemRepository actionItemRepository;

    private final AIService aiService;

    private final ObjectMapper objectMapper;

    public MeetingResponse createMeeting(
            CreateMeetingRequest request
    ) {

        try {

            log.info(
                    "Creating meeting with title: {}",
                    request.getTitle()
            );

            String transcriptJson =
                    objectMapper.writeValueAsString(
                            request.getTranscript()
                    );

            log.info(
                    "Transcript converted to JSON successfully"
            );

            MeetingResponse aiResponse =
                    aiService.analyzeTranscript(
                            transcriptJson
                    );

            log.info(
                    "AI transcript analysis completed"
            );

            Meeting meeting =
                    new Meeting();

            meeting.setTitle(
                    request.getTitle()
            );

            meeting.setTranscript(
                    transcriptJson
            );

            meeting.setMeetingDate(
                    request.getMeetingDate()
            );

            meeting.setSummaryJson(
                    objectMapper.writeValueAsString(
                            aiResponse.getSummary()
                    )
            );

            meeting.setDecisionsJson(
                    objectMapper.writeValueAsString(
                            aiResponse.getDecisions()
                    )
            );

            meeting.setFollowUpsJson(
                    objectMapper.writeValueAsString(
                            aiResponse.getFollowUpSuggestions()
                    )
            );

            meetingRepository.save(
                    meeting
            );

            log.info(
                    "Meeting persisted successfully with id: {}",
                    meeting.getId()
            );

            aiResponse.getActionItems()
                    .forEach(item -> {

                        try {

                            log.info(
                                    "Persisting AI generated action item for assignee: {}",
                                    item.getAssignee()
                            );

                            ActionItem actionItem = new ActionItem();

                            actionItem.setTask(item.getTask());

                            actionItem.setAssignee(item.getAssignee());

                            actionItem.setMeeting(meeting);

                            actionItem.setCitationsJson(objectMapper.writeValueAsString(item.getCitations()));

                            actionItemRepository.save(actionItem);
                            log.info("Action item persisted successfully");

                        } catch (Exception e) {
                            log.error("Failed to persist action item", e);
                            throw new RuntimeException("Failed to persist action items");
                        }
                    });

            log.info("Meeting workflow completed successfully");

            return aiResponse;

        } catch (Exception e) {

            log.error("Failed to create meeting", e);

            throw new RuntimeException(
                    "Failed to create meeting"
            );
        }
    }
}