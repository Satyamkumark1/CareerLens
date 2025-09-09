package CareerLens.demo.services;
import CareerLens.demo.exceptions.BadRequestException;
import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.model.Application;
import CareerLens.demo.model.ApplicationStatus;
import CareerLens.demo.model.Internship;
import CareerLens.demo.model.StudentProfile;
import CareerLens.demo.payloads.ApplicationDTOs.*;
import CareerLens.demo.repository.ApplicationRepository;
import CareerLens.demo.repository.InternshipRepository;
import CareerLens.demo.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceimpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final InternshipRepository internshipRepository;
    private final ModelMapper modelMapper;

    public List<ApplicationResponse> getUserApplications(Long userId) {
        List<Application> applications = applicationRepository.findByStudentUserId(userId);
        return applications.stream()
                .map(this::convertToResponse)
                .toList();
    }

    public Page<ApplicationResponse> getUserApplications(Long userId, Pageable pageable) {
        Page<Application> applications = applicationRepository.findByStudentUserId(userId, pageable);
        return applications.map(this::convertToResponse);
    }

    @Transactional
    public ApplicationResponse applyToInternship(Long userId, ApplicationRequest request) {
        // Check if student profile exists
        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Please complete your student profile before applying"));

        // Get internship
        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: " + request.getInternshipId()));

        // Check if internship is active
        if (!internship.getIsActive()) {
            throw new ResourceNotFoundException("This internship is no longer available");
        }

        // Check if application deadline has passed
        if (internship.getApplicationDeadline().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new ResourceNotFoundException("Application deadline has passed for this internship");
        }

        // Check if already applied
        if (applicationRepository.existsByStudentUserIdAndInternshipId(userId, request.getInternshipId())) {
            throw new ResourceNotFoundException("You have already applied to this internship");
        }

        // Create application
        Application application = new Application();
        application.setStudent(student);
        application.setInternship(internship);
        application.setCoverLetter(request.getCoverLetter());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedAt(LocalDateTime.now());

        Application savedApplication = applicationRepository.save(application);
        log.info("User {} applied to internship {}", userId, request.getInternshipId());

        return convertToResponse(savedApplication);
    }

    @Transactional
    public ApplicationResponse updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        application.setStatus(request.getStatus());
        application.setStatusUpdatedAt(LocalDateTime.now());

        Application updatedApplication = applicationRepository.save(application);
        log.info("Application {} status updated to {}", applicationId, request.getStatus());

        return convertToResponse(updatedApplication);
    }

    public ApplicationDetailResponse getApplicationDetails(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        return convertToDetailResponse(application);
    }

    public Page<ApplicationDetailResponse> getApplicationsByInternship(Long internshipId, Pageable pageable) {
        Page<Application> applications = applicationRepository.findByInternshipId(internshipId, pageable);
        return applications.map(this::convertToDetailResponse);
    }

    public ApplicationStatsDto getApplicationStats(Long userId) {
        ApplicationStatsDto stats = new ApplicationStatsDto();
        stats.setTotalApplications(applicationRepository.countByUserId(userId));

        // Count applications by status
        stats.setPendingCount(applicationRepository.countByInternshipIdAndStatus(userId, ApplicationStatus.PENDING));
        stats.setUnderReviewCount(applicationRepository.countByInternshipIdAndStatus(userId, ApplicationStatus.UNDER_REVIEW));
        stats.setShortlistedCount(applicationRepository.countByInternshipIdAndStatus(userId, ApplicationStatus.SHORTLISTED));
        stats.setAcceptedCount(applicationRepository.countByInternshipIdAndStatus(userId, ApplicationStatus.ACCEPTED));
        stats.setRejectedCount(applicationRepository.countByInternshipIdAndStatus(userId, ApplicationStatus.REJECTED));

        return stats;
    }

    @Transactional
    public void withdrawApplication(Long userId, Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        // Verify the application belongs to the user
        if (!application.getStudent().getUser().getId().equals(userId)) {
            throw new BadRequestException("You can only withdraw your own applications");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setStatusUpdatedAt(LocalDateTime.now());

        applicationRepository.save(application);
        log.info("User {} withdrew application {}", userId, applicationId);
    }

    @Override
    public List<ApplicationResponse> getUserApplicationss(Long id) {
        return List.of();
    }

    private ApplicationResponse convertToResponse(Application application) {
        ApplicationResponse response = modelMapper.map(application, ApplicationResponse.class);
        response.setInternshipId(application.getInternship().getId());
        response.setInternshipTitle(application.getInternship().getTitle());
        response.setOrganization(application.getInternship().getOrganization());
        return response;
    }

    private ApplicationDetailResponse convertToDetailResponse(Application application) {
        ApplicationDetailResponse response = modelMapper.map(application, ApplicationDetailResponse.class);
        response.setStudentId(application.getStudent().getId());
        response.setStudentName(application.getStudent().getUser().getFirstName() + " " +
                application.getStudent().getUser().getLastName());
        response.setInternshipId(application.getInternship().getId());
        response.setInternshipTitle(application.getInternship().getTitle());
        response.setOrganization(application.getInternship().getOrganization());
        return response;
    }
}
