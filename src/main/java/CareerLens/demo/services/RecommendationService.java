package CareerLens.demo.services;

import CareerLens.demo.controller.PythonIntegrationService;
import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.model.Recommendation;
import CareerLens.demo.model.StudentProfile;
import CareerLens.demo.payloads.InternshipDTOs.InternshipDetailResponse;
import CareerLens.demo.payloads.RecommendationDTOs.*;
import CareerLens.demo.repository.RecommendationRepository;
import CareerLens.demo.repository.InternshipRepository;
import CareerLens.demo.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final InternshipService internshipService;
    private final InternshipRepository internshipRepository;
    private final PythonIntegrationService pythonIntegrationService;
    private final ModelMapper modelMapper;

    @Transactional
    public RecommendationResponse getRecommendationsForUser(Long userId) {
        // Get student profile
        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for user id: " + userId));

        // Prepare request for Python service
        RecommendationRequest request = createRecommendationRequest(student);

        // Get recommendations from Python service
        List<RecommendationScore> aiRecommendations = pythonIntegrationService.getRecommendations(request);

        // If no recommendations from AI, get fallback (most recent internships)
        if (aiRecommendations.isEmpty()) {
            aiRecommendations = getFallbackRecommendations();
        }

        // Get top 5 recommendations
        List<RecommendationScore> topRecommendations = aiRecommendations.stream()
                .limit(5)
                .collect(Collectors.toList());

        // Convert to response and save tracking records
        RecommendationResponse response = new RecommendationResponse();
        response.setStudentId(userId);
        response.setGeneratedAt(LocalDateTime.now().toString());
        response.setRecommendations(convertToRecommendationDetails(topRecommendations, student));

        // Save recommendation records for tracking
        saveRecommendationRecords(student, topRecommendations);

        return response;
    }

    @Transactional
    public void processFeedback(Long userId, RecommendationFeedbackRequest feedback) {
        // Find the recommendation record
        Recommendation recommendation = recommendationRepository.findById(feedback.getRecommendationId())
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));

        // Verify the recommendation belongs to the user
        if (!recommendation.getStudent().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You cannot provide feedback for this recommendation");
        }

        // Update feedback
        recommendation.setFeedback(feedback.getFeedback());
        recommendation.setFeedbackComments(feedback.getComments());
        recommendation.setFeedbackAt(LocalDateTime.now());
        recommendationRepository.save(recommendation);

        // Send feedback to Python service for model improvement
        pythonIntegrationService.sendFeedback(feedback);

        log.info("Feedback recorded for recommendation: {}", feedback.getRecommendationId());
    }

    @Transactional
    public void markAsViewed(Long userId, Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));

        if (!recommendation.getStudent().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Invalid recommendation for user");
        }

        if (!recommendation.getWasViewed()) {
            recommendation.setWasViewed(true);
            recommendation.setViewedAt(LocalDateTime.now());
            recommendationRepository.save(recommendation);
        }
    }

    @Transactional
    public void markAsApplied(Long userId, Long internshipId) {
        Recommendation recommendation = recommendationRepository.findByStudentUserIdAndInternshipId(userId, internshipId)
                .orElse(null);

        if (recommendation != null && !recommendation.getWasApplied()) {
            recommendation.setWasApplied(true);
            recommendation.setAppliedAt(LocalDateTime.now());
            recommendationRepository.save(recommendation);
        }
    }

    public RecommendationStatsDto getRecommendationStatistics() {
        RecommendationStatsDto stats = new RecommendationStatsDto();
        long total = recommendationRepository.count();
        long viewed = recommendationRepository.countByWasViewedTrue();
        long applied = recommendationRepository.countByWasAppliedTrue();
        stats.setTotalRecommendations(total);
        stats.setViewedCount(viewed);
        stats.setAppliedCount(applied);
        return stats;
    }

    public boolean checkPythonServiceHealth() {
        return pythonIntegrationService.isPythonServiceHealthy();
    }

    private RecommendationRequest createRecommendationRequest(StudentProfile student) {
        RecommendationRequest request = new RecommendationRequest();
        request.setStudentId(student.getId());
        request.setEducationLevel(student.getEducationLevel());
        request.setSkills(student.getSkills());
        request.setInterests(student.getInterests());
        request.setPreferredLocation(student.getPreferredLocation());
        request.setPreferredState(student.getPreferredState());
        request.setIsFirstGenLearner(student.getUser().isFirstGenLearner());
        return request;
    }

    private List<RecommendationDetail> convertToRecommendationDetails(List<RecommendationScore> scores, StudentProfile student) {
        return scores.stream().map(score -> {
            try {
                InternshipDetailResponse internship = internshipService.getInternshipById(score.getInternshipId(), student.getUser().getId());

                RecommendationDetail detail = new RecommendationDetail();
                detail.setInternship(internship);
                detail.setMatchScore(score.getScore());
                detail.setReason(generateMatchReason(student, internship, score.getScore()));

                return detail;
            } catch (Exception e) {
                log.warn("Failed to get internship details for ID: {}", score.getInternshipId());
                return null;
            }
        }).filter(detail -> detail != null).collect(Collectors.toList());
    }

    private String generateMatchReason(StudentProfile student, InternshipDetailResponse internship, Double score) {
        // Simple rule-based reason generation
        StringBuilder reason = new StringBuilder();

        if (score > 80) {
            reason.append("Excellent match! ");
        } else if (score > 60) {
            reason.append("Good match. ");
        } else {
            reason.append("Relevant opportunity. ");
        }

        // Check skill matches
        long skillMatches = student.getSkills().stream()
                .filter(skill -> internship.getRequiredSkills().contains(skill))
                .count();

        if (skillMatches > 0) {
            reason.append("Matches ").append(skillMatches).append(" required skills. ");
        }

        // Check location preference
        if (student.getPreferredLocation() != null &&
                student.getPreferredLocation().equalsIgnoreCase(internship.getLocation())) {
            reason.append("Matches your preferred location. ");
        }

        // Check interest match
        if (student.getInterests().contains(internship.getCategory())) {
            reason.append("Matches your interest in ").append(internship.getCategory()).append(". ");
        }

        // First-gen learner bonus mention
        if (student.getUser().isFirstGenLearner() && score > 70) {
            reason.append("Great opportunity for first-generation learners. ");
        }

        return reason.toString().trim();
    }

    private void saveRecommendationRecords(StudentProfile student, List<RecommendationScore> recommendations) {
        for (RecommendationScore score : recommendations) {
            Recommendation rec = new Recommendation();
            rec.setStudent(student);
            rec.setInternship(internshipRepository.findById(score.getInternshipId())
                    .orElseThrow(() -> new ResourceNotFoundException("Internship not found for id: " + score.getInternshipId())));
            rec.setMatchScore(score.getScore());
            rec.setRecommendedAt(LocalDateTime.now());
            recommendationRepository.save(rec);
        }
    }

    private List<RecommendationScore> getFallbackRecommendations() {
        // Fallback: return some recent internships with dummy scores
        return List.of(
                createDummyScore(1L, 75.0, "Popular choice among similar students"),
                createDummyScore(2L, 68.0, "Matches your preferred location"),
                createDummyScore(3L, 62.0, "Good fit for your skills")
        );
    }

    private RecommendationScore createDummyScore(Long internshipId, Double score, String reason) {
        RecommendationScore rs = new RecommendationScore();
        rs.setInternshipId(internshipId);
        rs.setScore(score);
        rs.setReason(reason);
        return rs;
    }
}