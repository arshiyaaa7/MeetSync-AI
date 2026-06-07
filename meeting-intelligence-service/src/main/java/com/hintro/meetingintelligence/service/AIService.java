package com.hintro.meetingintelligence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hintro.meetingintelligence.dtos.meeting.MeetingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AIService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public AIService(
            WebClient.Builder builder,
            ObjectMapper objectMapper
    ) {

        this.webClient =
                builder.baseUrl(
                        "https://api.groq.com/openai/v1"
                ).build();

        this.objectMapper = objectMapper;
    }

    public MeetingResponse analyzeTranscript(
            String transcript
    ) {

        try {

            log.info(
                    "Starting AI transcript analysis"
            );

            String prompt = """
                You are an AI meeting intelligence assistant.
                
                The CURRENT TRANSCRIPT is the only source of truth.
                    
                Analyze ONLY the transcript provided below. Ignore all previous conversations, examples, prompts, responses, outputs, and transcripts.
                    
                You are a strict information extraction system, not a creative assistant.
                    
                Rules:
                    
                   - Do not invent, infer, assume, or guess any information.
                   - Do not reuse content, timestamps, summaries, action items, decisions, or patterns from previous outputs.
                   - Use only information explicitly stated in the CURRENT transcript.
                   - If information is missing or unclear, return an empty array instead of guessing.
                   - Do not create attendees, action items, assignees, blockers, decisions, outcomes, or follow-ups unless explicitly mentioned.
                   - Action items require both:
                   - an explicit task
                   - an explicit assignee
                   - Decisions must be explicitly agreed, approved, or confirmed.
                   - Follow-up suggestions must be explicitly mentioned future meetings, reviews, syncs, or next steps.
                   - Every generated item must include at least one citation.
                   - Statements where a speaker explicitly says they "will" perform a task SHOULD be treated as action items.
                   - Citation timestamps must exactly match timestamps from the CURRENT transcript only.
                   - Prefer separate summary items instead of combining unrelated updates into one sentence.
                   - Problems, blockers, failures, or risks MUST NOT be converted into action items unless a speaker explicitly commits to resolving them.
                   - Never create action items with missing, unknown, inferred, or placeholder assignees.
                   - Action items without citations are invalid and MUST NOT be generated.
                   - Preserve the original meaning closely and avoid unsupported paraphrasing.
                    
                Output Rules:
                    
                   - Return ONLY valid raw JSON.
                   - Do not use markdown.
                   - Do not add explanations or extra text.
                   - Response must start with { and end with }.
                    
                Generate:
                    
                   - summary
                   - actionItems
                   - decisions
                   - followUpSuggestions
                
                Expected JSON schema:
                
                {
                  "summary": [
                    {
                      "text": "<summary_text>",
                      "citations": [
                        {
                          "timestamp": "<timestamp>"
                        }
                      ]
                    }
                  ],
                  "actionItems": [
                    {
                      "task": "<task>",
                      "assignee": "<assignee>",
                      "citations": [
                        {
                          "timestamp": "<timestamp>"
                        }
                      ]
                    }
                  ],
                  "decisions": [
                    {
                      "text": "<decision_text>",
                      "citations": [
                        {
                          "timestamp": "<timestamp>"
                        }
                      ]
                    }
                  ],
                  "followUpSuggestions": [
                    {
                      "text": "<follow_up_text>",
                      "citations": [
                        {
                          "timestamp": "<timestamp>"
                        }
                      ]
                    }
                  ]
                }
                
                Transcript:
                """ + transcript;

            Map<String, Object> requestBody = Map.of(
                    "model",
                    "llama-3.1-8b-instant",

                    "temperature",
                    0.1,

                    "messages",
                    List.of(
                            Map.of(
                                    "role",
                                    "user",

                                    "content",
                                    prompt
                            )
                    )
            );

            log.info(
                    "Sending request to Groq AI"
            );

            Map response = webClient.post()
                    .uri("/chat/completions")
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + groqApiKey
                    )
                    .contentType(
                            MediaType.APPLICATION_JSON
                    )
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.get("choices");

            Map<String, Object> message =
                    (Map<String, Object>) choices
                            .get(0)
                            .get("message");

            String content =
                    message.get("content")
                            .toString();

            content = content
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            log.info(
                    "AI transcript analysis completed successfully"
            );

            return objectMapper.readValue(
                    content,
                    MeetingResponse.class
            );

        } catch (Exception e) {

            log.error(
                    "Failed to analyze transcript",
                    e
            );

            throw new RuntimeException(
                    "Failed to analyze transcript"
            );
        }
    }
}