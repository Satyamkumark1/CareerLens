package CareerLens.demo.services;

import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.model.Internship;
import CareerLens.demo.payloads.InternshipDTOs.InternshipDetailResponse;
import CareerLens.demo.payloads.InternshipDTOs.InternshipFilter;
import CareerLens.demo.payloads.InternshipDTOs.InternshipRequest;
import CareerLens.demo.payloads.InternshipDTOs.InternshipResponse;
import CareerLens.demo.repository.ApplicationRepository;
import CareerLens.demo.repository.InternshipRepository;
import CareerLens.demo.specification.InternshipSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private ApplicationRepository applicationRepository;

    public Page<InternshipResponse> getAllInternships(InternshipFilter filter, Pageable pageable) {
        Specification<Internship> spec = Specification.where(InternshipSpecifications.isActive());

        if (filter != null) {
            if (filter.getCategory() != null) {
                spec = spec.and(InternshipSpecifications.hasCategory(filter.getCategory()));
            }
            if (filter.getLocation() != null) {
                spec = spec.and(InternshipSpecifications.hasLocation(filter.getLocation()));
            }
            if (filter.getState() != null) {
                spec = spec.and(InternshipSpecifications.hasState(filter.getState()));
            }
            if (filter.getSearch() != null) {
                spec = spec.and(InternshipSpecifications.containsText(filter.getSearch()));
            }
            if (filter.getSkill() != null) {
                spec = spec.and(InternshipSpecifications.hasSkill(filter.getSkill()));
            }
        }

        Page<Internship> internships = internshipRepository.findAll(spec, pageable);
        return internships.map(this::convertToResponse);
    }

    public InternshipDetailResponse getInternshipById(Long id, Long userId) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: " ,"", id));

        InternshipDetailResponse response = modelMapper.map(internship, InternshipDetailResponse.class);

        // Check if user has applied to this internship
        response.setHasApplied(hasUserApplied(id, userId));

        return response;
    }

    public InternshipResponse createInternship(InternshipRequest request) {
        Internship internship = modelMapper.map(request, Internship.class);
        Internship savedInternship = internshipRepository.save(internship);
        log.info("New internship created: {}", request.getTitle());
        return convertToResponse(savedInternship);
    }

    public InternshipResponse updateInternship(Long id, InternshipRequest request) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: ","", + id));

        // Update fields
        modelMapper.map(request, internship);

        Internship updatedInternship = internshipRepository.save(internship);
        log.info("Internship updated: {}", request.getTitle());

        return convertToResponse(updatedInternship);
    }

    public void deleteInternship(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found with id: " ,"", + id));

        internship.setIsActive(false);
        internshipRepository.save(internship);
        log.info("Internship soft-deleted: {}", internship.getTitle());
    }

    public List<String> getAllCategories() {
        return internshipRepository.findAllDistinctCategories();
    }

    public List<String> getAllLocations() {
        return internshipRepository.findAllDistinctLocations();
    }

    @Override
    public List<InternshipResponse> addMultile(List<InternshipRequest> request) {

        List<Internship> internships = request.stream()
                .map(internshipRequest -> modelMapper.map(internshipRequest, Internship.class))
                .toList();

        List<Internship> savedInternships = internshipRepository.saveAll(internships);

        List<InternshipResponse> responses = savedInternships.stream()
                .map(internship -> modelMapper.map(internship, InternshipResponse.class))
                .toList();

        return responses;
    }

    @Override
    public List<InternshipResponse> createInternships(List<InternshipRequest> requests) {
        return List.of();
    }

    public List<String> getAllStates() {
        return internshipRepository.findAllDistinctStates();
    }

    private InternshipResponse convertToResponse(Internship internship) {
        return modelMapper.map(internship, InternshipResponse.class);
    }

    public Boolean hasUserApplied(Long internshipId, Long userId) {
        if (userId == null) {
            return false;
        }
        return applicationRepository.existsByStudentUserIdAndInternshipId(userId, internshipId);
    }
}