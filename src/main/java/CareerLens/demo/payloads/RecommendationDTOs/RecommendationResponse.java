package CareerLens.demo.RecommendationDTOs;

import CareerLens.demo.payloads.InternshipDTOs.InternshipResponse;
import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private Long studentId;
    private List<RecommendationDetail> recommendations;
    private String generatedAt;
}

@Data
class RecommendationDetail {
    private InternshipResponse internship;
    private Double matchScore;
    private String reason;
}