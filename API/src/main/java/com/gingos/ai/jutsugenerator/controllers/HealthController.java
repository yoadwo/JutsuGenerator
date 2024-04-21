package com.gingos.ai.jutsugenerator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gingos.ai.jutsugenerator.handlers.JutsuHandler;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/health")

public class HealthController {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @GetMapping()
    public String isAlive(){
        return "OK";
    }

}
