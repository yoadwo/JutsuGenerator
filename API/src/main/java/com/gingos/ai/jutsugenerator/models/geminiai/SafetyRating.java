package com.gingos.ai.jutsugenerator.models.geminiai;

import lombok.Data;

@Data
public class SafetyRating{
    private String category;
    private String probability;
}
