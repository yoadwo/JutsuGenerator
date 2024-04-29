package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SafetySettings {
    private String category;
    private String threshold;
}
