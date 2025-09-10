package CareerLens.demo.payloads.RecommendationDTOs;

import lombok.Data;

@Data
public class RecommendationStatsDto {
    private long totalRecommendations;
    private long viewedCount;
    private long appliedCount;
}


