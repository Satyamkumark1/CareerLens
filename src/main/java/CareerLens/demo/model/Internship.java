package CareerLens.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "internships")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "internship_required_skills",
            joinColumns = @JoinColumn(name = "internship_id"))
    @Column(name = "skill")
    private List<String> requiredSkills;

    @ElementCollection
    @CollectionTable(name = "internship_preferred_skills",
            joinColumns = @JoinColumn(name = "internship_id"))
    @Column(name = "skill")
    private List<String> preferredSkills;

    private String organization;
    private String category; // e.g., "IT", "Healthcare", "Education"
    private String location;
    private String state;

    private String duration; // e.g., "3 months", "6 months"
    private String stipend;  // e.g., "10,000/month", "Unpaid"

    private LocalDate applicationDeadline;
    private LocalDate startDate;

    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}