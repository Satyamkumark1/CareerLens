package CareerLens.demo.payloads.InternshipDTOs;



import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InternshipDetailResponse {
    private Long id;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private List<String> preferredSkills;
    private String organization;
    private String category;
    private String location;
    private String state;
    private String duration;
    private String stipend;
    private LocalDate applicationDeadline;
    private LocalDate startDate;
    private Boolean isActive;
    private Boolean hasApplied; // For logged-in users to see if they've applied
}