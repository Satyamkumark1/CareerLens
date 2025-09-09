package CareerLens.demo.payloads.ApplicationDTOs;


import CareerLens.demo.model.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationDetailResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long internshipId;
    private String internshipTitle;
    private String organization;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime statusUpdatedAt;
    private String coverLetter;
}