package CareerLens.demo.payloads.ApplicationDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotNull(message = "Internship ID is required")
    private Long internshipId;

    private  String coverLetter;
}
