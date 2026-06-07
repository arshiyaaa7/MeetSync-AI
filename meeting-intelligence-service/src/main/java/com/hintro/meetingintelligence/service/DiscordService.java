package com.hintro.meetingintelligence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordService {

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    private final WebClient.Builder builder;

    public void sendReminder(
            String message
    ) {

        try {

            log.info(
                    "Sending Discord reminder"
            );

            WebClient webClient =
                    builder.build();

            Map<String, String> body =
                    Map.of(
                            "content",
                            message
                    );

            webClient.post()
                    .uri(webhookUrl)
                    .contentType(
                            MediaType.APPLICATION_JSON
                    )
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            log.info(
                    "Discord reminder sent successfully"
            );

        } catch (Exception e) {

            log.error(
                    "Failed to send Discord reminder",
                    e
            );

            throw new RuntimeException(
                    "Failed to send Discord reminder"
            );
        }
    }
}