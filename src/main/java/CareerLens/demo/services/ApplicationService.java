package CareerLens.demo.services;

import CareerLens.demo.payloads.ApplicationDTOs.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {
    ApplicationResponse applyToInternship(Long id, @Valid ApplicationRequest request);


    Page<ApplicationResponse> getUserApplications(Long id, Pageable pageable);

    ApplicationStatsDto getApplicationStats(Long id);

    ApplicationDetailResponse getApplicationDetails(Long applicationId);

    Page<ApplicationDetailResponse> getApplicationsByInternship(Long internshipId, Pageable pageable);

    ApplicationResponse updateApplicationStatus(Long applicationId, @Valid UpdateApplicationStatusRequest request);

    void withdrawApplication(Long id, Long applicationId);

    List<ApplicationResponse> getUserApplicationss(Long id);
}
