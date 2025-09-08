package CareerLens.demo.config;

import CareerLens.demo.model.Internship;
import CareerLens.demo.model.User;
import CareerLens.demo.model.UserRole;
import CareerLens.demo.repository.InternshipRepository;
import CareerLens.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev") // Only run in development profile
public class DataLoader implements CommandLineRunner {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (internshipRepository.count() == 0) {
            loadSampleInternships();
        }
        if (!userRepository.existsByEmail("admin@careerlens.com")) {
            createAdminUser();
        }
    }

    private void loadSampleInternships() {
        List<Internship> internships = List.of(
                createInternship(
                        "Software Developer Intern",
                        "Join our dynamic team as a Software Developer Intern. Work on real projects using Java, Spring Boot, and modern web technologies.",
                        List.of("Java", "Spring Boot", "SQL", "REST APIs"),
                        List.of("Git", "Docker", "AWS"),
                        "Tech Solutions Ltd.",
                        "IT",
                        "Bhubaneswar",
                        "Odisha",
                        "3 months",
                        "15,000/month",
                        LocalDate.now().plusDays(30),
                        LocalDate.now().plusDays(45)
                ),
                createInternship(
                        "Data Science Intern",
                        "Exciting opportunity for Data Science enthusiasts. Work with Python, ML algorithms, and data visualization tools.",
                        List.of("Python", "Machine Learning", "Statistics"),
                        List.of("TensorFlow", "PyTorch", "SQL"),
                        "Data Insights Inc.",
                        "IT",
                        "Remote",
                        null,
                        "6 months",
                        "12,000/month",
                        LocalDate.now().plusDays(45),
                        LocalDate.now().plusDays(60)
                ),
                createInternship(
                        "Marketing Intern",
                        "Help us create engaging marketing campaigns. Perfect for students interested in digital marketing and social media.",
                        List.of("Communication", "Creativity", "Social Media"),
                        List.of("Content Writing", "SEO", "Analytics"),
                        "Creative Minds Agency",
                        "Marketing",
                        "Cuttack",
                        "Odisha",
                        "3 months",
                        "8,000/month",
                        LocalDate.now().plusDays(25),
                        LocalDate.now().plusDays(40)
                )
        );

        internshipRepository.saveAll(internships);
        System.out.println("Sample internships loaded successfully!");
    }

    private Internship createInternship(String title, String description, List<String> requiredSkills,
                                        List<String> preferredSkills, String organization, String category,
                                        String location, String state, String duration, String stipend,
                                        LocalDate deadline, LocalDate startDate) {
        Internship internship = new Internship();
        internship.setTitle(title);
        internship.setDescription(description);
        internship.setRequiredSkills(requiredSkills);
        internship.setPreferredSkills(preferredSkills);
        internship.setOrganization(organization);
        internship.setCategory(category);
        internship.setLocation(location);
        internship.setState(state);
        internship.setDuration(duration);
        internship.setStipend(stipend);
        internship.setApplicationDeadline(deadline);
        internship.setStartDate(startDate);
        internship.setIsActive(true);
        return internship;
    }

    private void createAdminUser() {
        User admin = new User();
        admin.setEmail("admin@careerlens.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(UserRole.ADMIN);
        admin.setAccountVerified(true);
        userRepository.save(admin);
        System.out.println("Admin user created: admin@careerlens.com / admin123");
    }
}