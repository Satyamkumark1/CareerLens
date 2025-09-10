package CareerLens.demo.controller;

import CareerLens.demo.payloads.RecommendationDTOs.PythonRecommendationResponse;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationFeedbackRequest;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationRequest;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PythonIntegrationService {

    @Value("${app.python-service.url}")
    private String pythonServiceUrl;

    @Autowired
    private  RestTemplate restTemplate;

    public List<RecommendationScore> getRecommendations(RecommendationRequest request) {
        try {
            String url = pythonServiceUrl + "/recommendations/generate";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<RecommendationRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<PythonRecommendationResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, PythonRecommendationResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getRecommendations();
            }

            log.warn("Python service returned non-success status: {}", response.getStatusCode());
            return getFallbackRecommendations();

        } catch (Exception e) {
            log.error("Error calling Python recommendation service: {}", e.getMessage());
            return getFallbackRecommendations();
        }
    }

    public void sendFeedback(RecommendationFeedbackRequest feedback) {
        try {
            String url = pythonServiceUrl + "/recommendations/feedback";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RecommendationFeedbackRequest> entity = new HttpEntity<>(feedback, headers);

            restTemplate.postForEntity(url, entity, Void.class);
            log.info("Feedback sent to Python service for internship: {}", feedback.getInternshipId());

        } catch (Exception e) {
            log.error("Failed to send feedback to Python service: {}", e.getMessage());
            // Don't throw exception for feedback failures
        }
    }

    private List<RecommendationScore> getFallbackRecommendations() {
        log.info("Using fallback recommendation logic");
        // In a real scenario, you might implement a simple rule-based fallback
        return Collections.emptyList();
    }

    // Health check method
    public boolean isPythonServiceHealthy() {
        try {
            String url = pythonServiceUrl + "/health";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.warn("Python service health check failed: {}", e.getMessage());
            return false;
        }
    }
}
