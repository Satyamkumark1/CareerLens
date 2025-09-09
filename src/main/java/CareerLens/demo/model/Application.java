package CareerLens.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"student_id", "internship_id"}))
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name = "internship_id",nullable = false)
    private Internship internship;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime appliedAt;

    @UpdateTimestamp
    private LocalDateTime statusUpdatedAt;

    @Column(length = 2000)
    private String coverLetter;

}
