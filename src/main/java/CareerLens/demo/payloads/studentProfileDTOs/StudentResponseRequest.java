package CareerLens.demo.payloads.studentProfileDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentResponseRequest {

    @NotNull(message = "Education level is required")
    private  String educationLevel;

    private  String collegeName;

    private Double cgpa;

    private List<String> skills;

    private  List<String> interests; // e.g., ["IT", "Healthcare", "Education"]

    private String preferredLocation; // Preferred city for internship
    private String preferredState;    // Preferred state for internship

    private String bio;


}
