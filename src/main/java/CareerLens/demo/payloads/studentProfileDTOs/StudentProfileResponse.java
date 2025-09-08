package CareerLens.demo.payloads;



import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentProfileResponse {
    private Long id;                // Profile ID
    private Long userId;            // Associated user ID
    private String educationLevel;
    private String collegeName;
    private Double cgpa;
    private List<String> skills;
    private List<String> interests;
    private String preferredLocation;
    private String preferredState;
    private String bio;
    private String resumeUrl;       // For future file upload feature
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}