package com.gingos.ai.jutsugenerator.models.edenai;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
public class EdenAIRequest {
    private String providers;
    private String text;
    private String settings;
    private String resolution;
    private String fallbackProviders;
}
