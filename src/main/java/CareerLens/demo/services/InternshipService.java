package CareerLens.demo.services;

import CareerLens.demo.payloads.InternshipDetailResponse;
import CareerLens.demo.payloads.InternshipFilter;
import CareerLens.demo.payloads.InternshipRequest;
import CareerLens.demo.payloads.InternshipResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InternshipService {

    InternshipResponse createInternship(@Valid InternshipRequest request);

    InternshipDetailResponse getInternshipById(Long id, Long userId);

    InternshipResponse updateInternship(Long id, @Valid InternshipRequest request);

    Page<InternshipResponse> getAllInternships(InternshipFilter filter, Pageable pageable);

    void deleteInternship(Long id);

    List<String> getAllCategories();

    List<String> getAllStates();

    List<String> getAllLocations();

    List<InternshipResponse> addMultile(@Valid List<InternshipRequest> request);
}
