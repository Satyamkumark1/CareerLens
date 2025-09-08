package CareerLens.demo.payloads.InternshipDTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InternshipRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Required skills cannot be null")
    private List<String> requiredSkills;

    private List<String> preferredSkills;

    @NotBlank(message = "Organization is required")
    private String organization;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    private String state;

    private String duration;
    private String stipend;

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    private LocalDate startDate;

    private Boolean isActive = true;
}



