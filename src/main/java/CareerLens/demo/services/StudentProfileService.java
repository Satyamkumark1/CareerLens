package CareerLens.demo.services;

import CareerLens.demo.payloads.studentProfileDTOs.StudentProfileResponse;
import CareerLens.demo.payloads.studentProfileDTOs.StudentResponseRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface StudentProfileService {

    StudentProfileResponse createProfile(@Valid StudentResponseRequest studentResponseRequest, Long id);


    StudentProfileResponse getStudentProfile(Long profileId);


    StudentProfileResponse updateProfile(Long id, @Valid StudentResponseRequest request);
}
