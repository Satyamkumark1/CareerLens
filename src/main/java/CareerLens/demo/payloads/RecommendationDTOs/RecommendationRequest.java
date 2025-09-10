package CareerLens.demo.payloads.RecommendationDTOs;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequestResponse {
    private Long studentId;
    private String educationLevel;
    private List<String> skills;
    private List<String> interests;
    private String preferredLocation;
    private String preferredState;
    private Boolean isFirstGenLearner;
}