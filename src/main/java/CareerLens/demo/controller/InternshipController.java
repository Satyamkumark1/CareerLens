package CareerLens.demo.controller;


import CareerLens.demo.payloads.InternshipDetailResponse;
import CareerLens.demo.payloads.InternshipFilter;
import CareerLens.demo.payloads.InternshipRequest;
import CareerLens.demo.payloads.InternshipResponse;
import CareerLens.demo.security.UserPrincipal;
import CareerLens.demo.services.InternshipService;
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
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<Page<InternshipResponse>> getAllInternships(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String skill) {

        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        InternshipFilter filter = new InternshipFilter(category, location, state, search, skill);
        Page<InternshipResponse> internships = internshipService.getAllInternships(filter, pageable);

        return ResponseEntity.ok(internships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipDetailResponse> getInternshipById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        InternshipDetailResponse internship = internshipService.getInternshipById(id, userId);
        return ResponseEntity.ok(internship);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<InternshipResponse> createInternship(@Valid @RequestBody InternshipRequest request) {
        InternshipResponse internship = internshipService.createInternship(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(internship);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<List<InternshipResponse>> addMuiltiple(@Valid @RequestBody List<InternshipRequest> request) {
        List<InternshipResponse> internship = internshipService.addMultile(request);
        return new ResponseEntity<>(internship,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<InternshipResponse> updateInternship(
            @PathVariable Long id,
            @Valid @RequestBody InternshipRequest request) {
        InternshipResponse internship = internshipService.updateInternship(id, request);
        return ResponseEntity.ok(internship);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getInternshipCategories() {
        List<String> categories = internshipService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<String>> getInternshipLocations() {
        List<String> locations = internshipService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/states")
    public ResponseEntity<List<String>> getInternshipStates() {
        List<String> states = internshipService.getAllStates();
        return ResponseEntity.ok(states);
    }
}