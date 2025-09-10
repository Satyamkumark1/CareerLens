package CareerLens.demo.payloads.RecommendationDTOs;

import lombok.Data;

import java.util.List;

@Data
public class PythonRecommendationResponse {
    private List<RecommendationScore> recommendations;
    private String modelVersion;
    private Long processingTimeMs;
}

