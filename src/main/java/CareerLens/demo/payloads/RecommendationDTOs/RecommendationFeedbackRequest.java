package CareerLens.demo.payloads.RecommendationDTOs;

import lombok.Data;

@Data
public class RecommendationFeedbackRequest {
    private Long recommendationId;
    private Long internshipId;
    private String feedback; // "LIKE", "DISLIKE"
    private String comments;
}