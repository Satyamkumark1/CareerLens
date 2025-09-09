package CareerLens.demo.controller;


import CareerLens.demo.payloads.ApplicationDTOs.*;
import CareerLens.demo.security.UserPrincipal;
import CareerLens.demo.services.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getUserApplications(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        List<ApplicationResponse> applications = applicationService
                .getUserApplicationss(currentUser.getId());
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ApplicationResponse>> getUserApplicationsPaginated(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appliedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ApplicationResponse> applications = applicationService.getUserApplications(currentUser.getId(), pageable);
        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> applyToInternship(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ApplicationRequest request) {
        ApplicationResponse application = applicationService.applyToInternship(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @GetMapping("/stats")
    public ResponseEntity<ApplicationStatsDto> getApplicationStats(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        ApplicationStatsDto stats = applicationService.getApplicationStats(currentUser.getId());
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> withdrawApplication(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long applicationId) {
        applicationService.withdrawApplication(currentUser.getId(), applicationId);
        return ResponseEntity.noContent().build();
    }

    // Admin endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationDetailResponse> getApplicationDetails(@PathVariable Long applicationId) {
        ApplicationDetailResponse application = applicationService.getApplicationDetails(applicationId);
        return ResponseEntity.ok(application);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<Page<ApplicationDetailResponse>> getApplicationsByInternship(
            @PathVariable Long internshipId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedAt"));
        Page<ApplicationDetailResponse> applications = applicationService.getApplicationsByInternship(internshipId, pageable);
        return ResponseEntity.ok(applications);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateApplicationStatusRequest request) {
        ApplicationResponse application = applicationService.updateApplicationStatus(applicationId, request);
        return ResponseEntity.ok(application);
    }
}