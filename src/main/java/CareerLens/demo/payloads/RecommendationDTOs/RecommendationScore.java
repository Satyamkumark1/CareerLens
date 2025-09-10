package CareerLens.demo.payloads.RecommendationDTOs;

import lombok.Data;

@Data
public class RecommendationScore {
    private Long internshipId;
    private Double score;
    private String reason;
}
