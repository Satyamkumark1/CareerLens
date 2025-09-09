package CareerLens.demo.repository;

import CareerLens.demo.model.Application;
import CareerLens.demo.model.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentUserId(Long userId);

    Page<Application> findByStudentUserId(Long userId, Pageable pageable);

    Optional<Application> findByStudentUserIdAndInternshipId(Long userId, Long internshipId);

    boolean existsByStudentUserIdAndInternshipId(Long userId, Long internshipId);

    List<Application> findByInternshipId(Long internshipId);

    Page<Application> findByInternshipId(Long internshipId, Pageable pageable);

    List<Application> findByStatus(ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.internship.organization = :organization")
    Page<Application> findByOrganization(@Param("organization") String organization, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.student.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.internship.id = :internshipId")
    Long countByInternshipId(@Param("internshipId") Long internshipId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.internship.id = :internshipId AND a.status = :status")
    Long countByInternshipIdAndStatus(@Param("internshipId") Long internshipId,
                                      @Param("status") ApplicationStatus status);
}