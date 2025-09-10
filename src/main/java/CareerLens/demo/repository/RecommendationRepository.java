package CareerLens.demo.repository;


import CareerLens.demo.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findByStudentUserIdOrderByMatchScoreDesc(Long userId);

    List<Recommendation> findByStudentUserIdAndRecommendedAtAfter(Long userId, LocalDateTime date);

    Optional<Recommendation> findByStudentUserIdAndInternshipId(Long userId, Long internshipId);

    @Query("SELECT r FROM Recommendation r WHERE r.student.user.id = :userId AND r.wasViewed = false")
    List<Recommendation> findUnviewedRecommendations(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Recommendation r WHERE r.student.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Recommendation r WHERE r.student.user.id = :userId AND r.wasApplied = true")
    Long countAppliedRecommendations(@Param("userId") Long userId);

    @Query("SELECT AVG(r.matchScore) FROM Recommendation r WHERE r.student.user.id = :userId")
    Double findAverageMatchScore(@Param("userId") Long userId);

    long countByWasViewedTrue();

    long countByWasAppliedTrue();
}