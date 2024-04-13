package com.gingos.ai.jutsugenerator.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "http.apikey")
public class RestClientsConfig {
    private String eden;

    public String getGemini() {
        return gemini;
    }

    public void setGemini(String gemini) {
        this.gemini = gemini;
    }

    private String gemini;

    public String getEden() {
        return eden;
    }

    public void setEden(String eden) {
        this.eden = eden;
    }
}
