package CareerLens.demo.payloads.RecommendationDTOs;

import CareerLens.demo.payloads.InternshipDTOs.InternshipDetailResponse;
import lombok.Data;

@Data
public class RecommendationDetail {
    private InternshipDetailResponse internship;
    private Double matchScore;
    private String reason;
}


