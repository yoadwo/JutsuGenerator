package com.gingos.ai.jutsugenerator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;

public interface GenerativeAIHttp {
    public String generateImage(String prompt) throws Exception;
    public JutsuInfo generateTechnique(String seals) throws JsonProcessingException;
}
