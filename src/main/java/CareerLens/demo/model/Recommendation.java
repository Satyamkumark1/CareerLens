package CareerLens.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    private Double matchScore;

    @CreationTimestamp
    private LocalDateTime recommendedAt;

    private Boolean wasViewed = false;
    private Boolean wasApplied = false;

    private String feedback;
    private String feedbackComments;

    private LocalDateTime viewedAt;
    private LocalDateTime appliedAt;
    private LocalDateTime feedbackAt;
}