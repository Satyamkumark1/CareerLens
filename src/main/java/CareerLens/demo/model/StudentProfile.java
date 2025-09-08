package CareerLens.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_profiles")
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String educationLevel; // "Class 12", "BA", "BSc", "BTech", etc.
    private String collegeName;
    private Double cgpa;

    @ElementCollection
    private List<String> skills; // ["MS Office", "Communication", "Python Basics"]

    @ElementCollection
    private List<String> interests; // ["IT", "Healthcare", "Education"]

    private String preferredLocation;
    private String preferredState;

    private String bio;
    private String resumeUrl;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}