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
    @Value("${gemini.api.model}")
    private String model;

    private final HttpClient client = HttpClient.newHttpClient();

    private final String promptCheckText = """
            Bạn là một nhà ngôn ngữ học chuyên về tiếng Việt, với hơn 50 năm kinh nghiệm chuyên nghiệp trong phân tích ngôn ngữ, diễn ngôn và giao tiếp xã hội.
            
            Nhiệm vụ của bạn là xác định xem câu sau có chứa từ ngữ thô tục, tục tĩu, chửi thề, lời lẽ xúc phạm hoặc bất kỳ hình thức ngôn ngữ không phù hợp trong giao tiếp văn minh nào hay không.
            
            Câu cần phân tích:
            "%s"
            
            QUY TẮC BẮT BUỘC:
            - Chỉ trả về một trong hai giá trị sau: true hoặc false
            - Không giải thích quyết định của bạn
            - Không thêm bất kỳ ký tự, dấu câu, khoảng trắng hay dòng trống nào
            - Không viết hoa
            - Không bao gồm dòng trống
            - Nếu câu có bất kỳ ngôn ngữ không phù hợp hoặc xúc phạm → trả về false
            - Nếu câu hoàn toàn phù hợp → trả về true
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
                            "https://generativelanguage.googleapis.com/v1/models/" + model + ":generateContent?key=" + apiKey
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // Gemini trả text, ta chỉ check true/false
            String result = response.body();

            if (response.body().contains("true")) {
                return "true";
            } else if (response.body().contains("false")) {
                return "false";
            } else {
                return "error";
            }

        } catch (Exception e) {
            return "error";
        }
    }
}
