package CareerLens.demo.services;

import CareerLens.demo.model.StudentProfile;
import org.springframework.stereotype.Service;

@Service
public interface StudentProfileService {
    void addStudent(StudentProfile studentProfile);
}
