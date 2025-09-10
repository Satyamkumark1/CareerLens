package CareerLens.demo.RecommendationDTOs;

import lombok.Data;

import java.util.List;

@Data
public class PythonRecommendationResponse {
    private List<RecommendationScore> recommendations;
    private String modelVersion;
    private Long processingTimeMs;
}

@Data
class RecommendationScore {
    private Long internshipId;
    private Double score;
    private String reason;
}