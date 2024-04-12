package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GeminiAIResponse {
    private List<Candidate> candidates;
    private PromptFeedback promptFeedback;

}
