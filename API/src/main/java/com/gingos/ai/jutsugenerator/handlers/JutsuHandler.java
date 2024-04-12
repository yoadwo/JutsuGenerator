package com.gingos.ai.jutsugenerator.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;
import com.gingos.ai.jutsugenerator.services.EdenAIHttp;
import com.gingos.ai.jutsugenerator.services.GeminiAIHttp;
import com.gingos.ai.jutsugenerator.services.GenerativeAIHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JutsuHandler {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private GenerativeAIHttp geminiAIHttp;

    @Autowired
    private GenerativeAIHttp edenAIHttp;

    public JutsuInfo createJutso(String seals) throws JsonProcessingException {
        JutsuInfo jutsuInfo = geminiAIHttp.generateTechnique(seals);
        logger.info("jutsu created: " + jutsuInfo.getName());
        return jutsuInfo;
    }

    public String generateImage(String prompt) throws Exception {
        String imageUrl =  edenAIHttp.generateImage(prompt);
        logger.info("got image url from edenAI generator");
        return imageUrl;
    }
}
