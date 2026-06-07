package com.hintro.meetingintelligence.dtos.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationResponse {

    private String candidateName;

    private String email;

    private String repositoryUrl;

    private String deployedUrl;

    private String externalIntegration;

    private List<String> features;
}