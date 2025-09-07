package CareerLens.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "internships")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ElementCollection
    private List<String> requiredSkills;

    @ElementCollection
    private List<String> preferredSkills;

    private String organization;
    private String category; // "IT", "Healthcare", "Education", etc.
    private String location;
    private String state;

    private String duration;
    private String stipend;

    private LocalDate applicationDeadline;
    private LocalDate startDate;

    private boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;
}