package com.youssef.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service d'intégration avec l'API OpenAI (ChatGPT).
 * Ce service permet d'envoyer des prompts à l'IA et de récupérer des réponses générées.
 * Il est utilisé pour fournir une assistance conversationnelle intelligente dans le bot Telegram.
 */
@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Génère une réponse textuelle à partir d'un message utilisateur en utilisant l'API OpenAI.
     *
     * @param userMessage Le message ou la question de l'utilisateur (incluant potentiellement le contexte système).
     * @return La réponse générée par l'IA, ou un message d'erreur en cas de problème.
     */
    public String generateResponse(String userMessage) {
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "Tu es un assistant bancaire."),
                Map.of("role", "user", "content", userMessage)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur de connexion avec l'IA.";
        }
    }
}