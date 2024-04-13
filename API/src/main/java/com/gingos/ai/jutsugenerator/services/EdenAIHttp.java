package com.gingos.ai.jutsugenerator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gingos.ai.jutsugenerator.models.JutsuInfo;
import com.gingos.ai.jutsugenerator.models.RestClientsConfig;
import com.gingos.ai.jutsugenerator.models.edenai.EdenAIRequest;
import com.gingos.ai.jutsugenerator.models.edenai.EdenAIResponse;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class EdenAIHttp implements GenerativeAIHttp {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private RestClient restClient;

    @Autowired
    private RestClientsConfig restClientsConfig;

    public EdenAIHttp(){
    }

    @PostConstruct
    public void init() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(APPLICATION_JSON));

        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .messageConverters(converters -> converters.add(converter))
                .baseUrl("https://api.edenai.run")
                .defaultHeader("Authorization", "Bearer " + restClientsConfig.getEden())
                .build();
    }


    @Override
    public String generateImage(String prompt) throws Exception {
        logger.info("sending image prompt to model, this usually takes a few seconds");
        EdenAIRequest edenAIRequest = buildBody(prompt);
        var response = restClient.post()
                .uri("v2/image/generation")
                .contentType(APPLICATION_JSON)
                .body(edenAIRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    logger.severe(String.format("%s: %s", res.getStatusText(), res.getStatusText()));
                    throw new BadRequestException(res.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    logger.severe(String.format("%s: %s", res.getStatusText(), res.getStatusText()));
                    throw new InternalError(res.getStatusText());
                })
                .body(EdenAIResponse.class);
        try {
            String imageResourceUrl = response.getReplicate().getItems().get(0).getImage_resource_url();
            return imageResourceUrl;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.err.printf("EdenAI Response changed: %s", e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }

    @Override
    public JutsuInfo generateTechnique(String seals) throws JsonProcessingException {
        throw new UnsupportedOperationException("Do not use EdenAI for text generation");
    }

    private EdenAIRequest buildBody(String prompt) throws JsonProcessingException {
        var settings = new HashMap<>();
        settings.put("replicate", "anime-style");

        return EdenAIRequest.builder()
                .providers("replicate")
                .text(prompt)
                .settings(new ObjectMapper().writeValueAsString(settings))
                .resolution("512x512")
                .fallbackProviders("")
                .build();
    }
}
