package CareerLens.demo.repository;

import CareerLens.demo.model.StudentProfile;
import CareerLens.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile,Long> {
    Long user(User user);

    Optional<StudentProfile> findByUserId(Long userId);

}
