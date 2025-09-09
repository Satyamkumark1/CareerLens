package CareerLens.demo.payloads.ApplicationDTOs;

import lombok.Data;

import java.time.LocalDateTime;

import CareerLens.demo.model.ApplicationStatus;

@Data
public class ApplicationResponse {

    private Long id;
    private Long internshipId;
    private String internshipTitle;
    private String organization;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime statusUpdatedAt;
    private String coverLetter;
}
