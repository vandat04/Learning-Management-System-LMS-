package com.lms.service.impl.common;

import com.lms.service.common.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final HttpClient client = HttpClient.newHttpClient();
    private final String promptCheckText = """
            You are a linguist specializing in the Vietnamese language, with more than 50 years
            of professional experience in the analysis of language, discourse, and social communication.
            
            Your task is to determine whether the following sentence contains profanity, vulgar language,
            swearing, insults, or any form of inappropriate or offensive expression in civil communication.
            
            Sentence to analyze:
            "%s"
            
            MANDATORY RULES:
            - Return ONLY one of the following two values: true or false
            - Do NOT explain your decision
            - Do NOT add any extra text, symbols, punctuation, or whitespace
            - Do NOT use uppercase letters
            - Do NOT include line breaks
            
            If the sentence contains any inappropriate or offensive language → return true
            If the sentence is completely appropriate → return false
            """;

    @Override
    public String checkText(String text) {
        String prompt = promptCheckText.formatted(text);

        String body = """
                {
                  "contents": [
                    {
                      "parts": [
                        { "text": "%s" }
                      ]
                    }
                  ]
                }
                """.formatted(prompt.replace("\"", "\\\""));
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://generativelanguage.googleapis.com/v1beta/models/" +
                                    "gemini-1.5-flash:generateContent?key=" + apiKey
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // Gemini trả text, ta chỉ check true/false
            return response.body().contains("true") ? "true" : "false";

        } catch (Exception e) {
            return "error";
        }
    }
}
