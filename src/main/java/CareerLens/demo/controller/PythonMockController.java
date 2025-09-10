package CareerLens.demo.controller;

import CareerLens.demo.payloads.RecommendationDTOs.PythonRecommendationResponse;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationRequest;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationScore;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.*;


import java.util.List;



@RestController
@RequestMapping("/mock/python")
@Profile("dev")
public class PythonMockController {

    @PostMapping("/recommendations/generate")
    public ResponseEntity<PythonRecommendationResponse> mockRecommendations(@RequestBody RecommendationRequest request) {
        // Mock response based on student profile
        List<RecommendationScore> recommendations = List.of(
                createRecommendation(1L, 92.5, "Excellent skill match and location preference"),
                createRecommendation(2L, 85.0, "Strong alignment with your interests"),
                createRecommendation(3L, 78.3, "Good fit for your educational background")
        );

        PythonRecommendationResponse response = new PythonRecommendationResponse();
        response.setRecommendations(recommendations);
        response.setModelVersion("mock-v1.0");
        response.setProcessingTimeMs(150L);

        return ResponseEntity.ok(response);
    }

    private RecommendationScore createRecommendation(Long internshipId, Double score, String reason) {
        RecommendationScore rs = new RecommendationScore();
        rs.setInternshipId(internshipId);
        rs.setScore(score);
        rs.setReason(reason);
        return rs;
    }
}