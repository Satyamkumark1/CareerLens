package CareerLens.demo.repository;


import CareerLens.demo.model.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long>, JpaSpecificationExecutor<Internship> {

    Page<Internship> findByIsActiveTrue(Pageable pageable);

    Page<Internship> findByCategoryAndIsActiveTrue(String category, Pageable pageable);

    Page<Internship> findByLocationContainingIgnoreCaseAndIsActiveTrue(String location, Pageable pageable);

    Page<Internship> findByStateContainingIgnoreCaseAndIsActiveTrue(String state, Pageable pageable);

    List<Internship> findByApplicationDeadlineAfterAndIsActiveTrue(LocalDate date);

    @Query("SELECT DISTINCT i.category FROM Internship i WHERE i.isActive = true")
    List<String> findAllDistinctCategories();

    @Query("SELECT DISTINCT i.location FROM Internship i WHERE i.isActive = true")
    List<String> findAllDistinctLocations();

    @Query("SELECT DISTINCT i.state FROM Internship i WHERE i.isActive = true AND i.state IS NOT NULL")
    List<String> findAllDistinctStates();

    @Query("SELECT i FROM Internship i WHERE i.isActive = true AND " +
            "(LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(i.organization) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Internship> searchActiveInternships(@Param("search") String search, Pageable pageable);
}