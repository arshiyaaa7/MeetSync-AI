package com.hintro.meetingintelligence.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HealthResponse {

    private String status;

    private String service;

    private String database;

    private String aiService;

    private String timestamp;
}