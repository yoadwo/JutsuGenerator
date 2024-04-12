package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class GenerationConfig{
    private double temperature;
    private int topK;
    private int topP;
    private int maxOutputTokens;
    private ArrayList<Object> stopSequences;
}
