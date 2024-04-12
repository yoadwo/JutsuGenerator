package com.gingos.ai.jutsugenerator.models;

import lombok.Data;

@Data
public class JutsuInfo {
    private String name;
    private String prompt;
    private String GeneratedImageUrl;
}
