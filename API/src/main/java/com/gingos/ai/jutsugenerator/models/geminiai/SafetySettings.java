package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SafetySettings {
    private String category;
    private String threshold;
}
