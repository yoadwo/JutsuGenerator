package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Data;

import java.util.List;

@Data
public class PromptFeedback {
    public List<SafetyRating> safetyRatings;

}
