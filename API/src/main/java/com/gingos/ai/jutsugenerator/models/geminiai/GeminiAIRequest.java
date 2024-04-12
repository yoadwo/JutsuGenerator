package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GeminiAIRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;
    private List<SafetySettings> safetySettings;
}