package com.gingos.ai.jutsugenerator.models;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "http.apikey")
public class RestClientsConfig implements Validator {
    @NotNull
    private String eden;

    @NotNull
    private String gemini;

    public String getEden() {
        return eden;
    }

    public void setEden(String eden) {
        this.eden = eden;
    }

    public String getGemini() {
        return gemini;
    }

    public void setGemini(String gemini) {
        this.gemini = gemini;
    }


    public boolean supports(Class<?> clazz) {
        return RestClientsConfig.class.isAssignableFrom(clazz);
    }


    public void validate(Object target, Errors errors) {
        RestClientsConfig properties = (RestClientsConfig) target;

        if (properties.getEden().equals("${EDENAI_APIKEY}")) {
            errors.rejectValue("eden", "field.eden.required",
                    "No API key was given provided for EDENAI_APIKEY");
        }
        if (properties.getGemini().equals("${GEMINI_APIKEY}")) {
            errors.rejectValue("gemini", "field.gemini.required",
                    "No API key was given provided for GEMINI_APIKEY");
        }

    }
}
