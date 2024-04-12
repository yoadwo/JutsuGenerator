package com.gingos.ai.jutsugenerator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;
import com.gingos.ai.jutsugenerator.models.geminiai.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class GeminiAIHttp implements GenerativeAIHttp {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private final String MODEL_NAME =  "gemini-1.0-pro";
    private final String API_KEY = "AIzaSyDrf93mNih8kaIggjzfgzMLfIfBy_Q0jdQ";

    private final RestClient restClient;
    public GeminiAIHttp(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(APPLICATION_JSON));

        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .messageConverters(converters -> converters.add(converter))
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    @Override
    public JutsuInfo generateTechnique(String seals) throws JsonProcessingException {
        logger.info("sending seals prompt to model");
        GeminiAIRequest geminiAI = buildPrompt(seals);
        var response = restClient.post()
                .uri("v1beta/models/{modelName}:generateContent?key={apiKey}", MODEL_NAME, API_KEY)
                .contentType(APPLICATION_JSON)
                .body(geminiAI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    logger.severe(String.format("%s: %s", res.getStatusText(), res.getStatusText()));
                    throw new BadRequestException(res.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    logger.severe(String.format("%s: %s", res.getStatusText(), res.getStatusText()));
                    throw new InternalError(res.getStatusText());
                })
                .body(GeminiAIResponse.class);
        logger.info("received jutsu from model");
        String generatedResponseText = response.getCandidates().get(0).getContent().getParts().get(0).getText();
        var jutsuInfo = new ObjectMapper().readValue(generatedResponseText, JutsuInfo.class);
        return jutsuInfo;
    }

    @Override
    public String generateImage(String prompt) throws Exception {
        throw new UnsupportedOperationException("Do not use EdenAI for image generation");
    }

    private JutsuInfo deserialize(String generatedResponseText) {
        String regex = "\\**[Tt]echnique [Nn]ame\\**:?\\**\\s*(.+)\\s*\\**[Ii]mage [Pp]rompt\\**:?\\**\\s*(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(generatedResponseText);

        if (matcher.find()) {
            String techniqueName = matcher.group(1).trim();
            String imagePrompt = matcher.group(2).trim();

            System.out.println("Technique Name: " + techniqueName);
            System.out.println("Image Prompt: " + imagePrompt);
            var ji = new JutsuInfo();
            ji.setName(techniqueName);
            ji.setPrompt(imagePrompt);
            return ji;
        } else {
            throw new IllegalStateException("should not fail parsing");
        }
    }

    private GeminiAIRequest buildPrompt(String seals) {
        var parts = new ArrayList<Part>();
        parts.add(new Part("""
                consider the naruto show and the hand signs seen in it.
                reminder: 12 basic hand signs (Rat, Ox, Tiger, Hare, Dragon , Snake , Horse , Ram , Monkey , Bird , Dog , Boar )
                 which combined create some ninja technique, usually elemental (ice, wind, fire, mud, forest, etc.) 
                 and animal- or from nature-shaped (dragon, wall, ball, needle, forest).  
                 per a given sequence of hand signs, return two outputs:
                  1.a name for the technique always in the form <element> style: <description> jutsu.
                  2. a prompt to generate an image of how such attack would hit a training dummy.
                  the response must be in a json format: {"name": "<name output>", "prompt": "<image prompt>" } 
                 here are examples:
                """));

        parts.add(new Part("sequence of hand sign names serpent, dragon, hare, tiger"));
        parts.add(new Part("technique name fire style: dragon flame jutsu"));
        parts.add(new Part("image prompt a large ball of fire with its front shaped like a dragon's head, hitting the training dummy, scorching it."));

        parts.add(new Part("sequence of hand sign names ox, rabbit, monkey"));
        parts.add(new Part("technique name thunder style: a thousand birds jutsu"));
        parts.add(new Part("image prompt an electric bolt shot from the sky, so loud it sounds like a thousands birds. the bold its the dummy, frying it."));

        parts.add(new Part("sequence of hand sign names rat, hare, dog"));
        parts.add(new Part("technique name ice style: wolf fang avalanche jutsu"));
        parts.add(new Part("image prompt an ice werewolf shot forward, biting the head off the training dummy."));

        parts.add(new Part("sequence of hand sign names serpent, hare, monkey, tiger"));
        parts.add(new Part("technique name fire style: misty flames dance jutsu"));
        parts.add(new Part("image prompt multiple small sized balls of fire, all hitting the dummy at the same time, leaving it as ashes."));

//        parts.add(new Part("sequence of hand sign names " + handseals));
//        parts.add(new Part("technique name "));

        parts.add(new Part("given the description and examples, generate a technique name and prompt for the given hand seals: " + seals));

        var generateConfig = GenerationConfig.builder().temperature(0.9).topK(1).topP(1).maxOutputTokens(2048).stopSequences(new ArrayList<>()).build();

        var contents = new Content();
        contents.setParts(parts);

        return GeminiAIRequest.builder()
                .contents(List.of(contents))
                .generationConfig(generateConfig)
                .build();
    }
}
