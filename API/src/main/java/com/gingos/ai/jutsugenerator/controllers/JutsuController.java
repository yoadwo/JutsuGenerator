package com.gingos.ai.jutsugenerator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gingos.ai.jutsugenerator.handlers.JutsuHandler;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/jutsu")
@CrossOrigin(origins = { "http://localhost:4200", "https://yoadwo.github.io/" })
public class JutsuController {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private JutsuHandler jutsuHandler;

    @PostMapping()
    public JutsuInfo createJutsu(@RequestParam("handseals") String handSeals) throws JsonProcessingException {
        logger.info("generating technique for: " + handSeals);
        return jutsuHandler.createJutso(handSeals);
    }

    @PostMapping("/visualize")
    public JutsuInfo visualizeJutsu(@RequestBody JutsuInfo jutsuInfo) throws Exception {
        logger.info("generating image for technique " + jutsuInfo.getName());
        var generatedImageUrl = jutsuHandler.generateImage(jutsuInfo.getPrompt());
        jutsuInfo.setGeneratedImageUrl(generatedImageUrl);
        return jutsuInfo;
    }
}
