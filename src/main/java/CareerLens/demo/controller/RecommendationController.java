package CareerLens.demo.controller;

import CareerLens.demo.payloads.RecommendationDTOs.RecommendationFeedbackRequest;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationResponse;
import CareerLens.demo.payloads.RecommendationDTOs.RecommendationStatsDto;
import CareerLens.demo.security.UserPrincipal;
import CareerLens.demo.services.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> getPersonalizedRecommendations(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        RecommendationResponse recommendations = recommendationService.getRecommendationsForUser(currentUser.getId());
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> provideRecommendationFeedback(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody RecommendationFeedbackRequest feedback) {
        recommendationService.processFeedback(currentUser.getId(), feedback);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{recommendationId}/viewed")
    public ResponseEntity<Void> markRecommendationAsViewed(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long recommendationId) {
        recommendationService.markAsViewed(currentUser.getId(), recommendationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/applied/{internshipId}")
    public ResponseEntity<Void> markRecommendationAsApplied(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long internshipId) {
        recommendationService.markAsApplied(currentUser.getId(), internshipId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<RecommendationStatsDto> getRecommendationStats() {
        RecommendationStatsDto stats = recommendationService.
                getRecommendationStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/health")
    public ResponseEntity<PythonServiceHealth> checkPythonServiceHealth() {
        boolean isHealthy = recommendationService.checkPythonServiceHealth();
        PythonServiceHealth health = new PythonServiceHealth(isHealthy, isHealthy ? "Connected" : "Disconnected");
        return ResponseEntity.ok(health);
    }
}

// Health response DTO
class PythonServiceHealth {
    private boolean healthy;
    private String status;

    public PythonServiceHealth(boolean healthy, String status) {
        this.healthy = healthy;
        this.status = status;
    }
    // getters and setters
}