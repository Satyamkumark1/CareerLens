package CareerLens.demo.services;


import CareerLens.demo.exceptions.BadRequestException;
import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.model.StudentProfile;
import CareerLens.demo.model.User;
import CareerLens.demo.payloads.StudentProfileResponse;
import CareerLens.demo.payloads.StudentResponseRequest;
import CareerLens.demo.repository.StudentProfileRepository;
import CareerLens.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileServiceImpl implements  StudentProfileService{

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;




    @Transactional
    @Override
    public StudentProfileResponse createProfile(StudentResponseRequest studentResponseRequest,
                                                Long id) {

        if (studentProfileRepository.existsById(id)){
            throw  new BadRequestException("Already exist");
        }

        User user =  userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("user","UserId",id));
        StudentProfile save = new StudentProfile();
        save.setUser(user);
        save.setBio(studentResponseRequest.getBio());
        save.setCgpa(studentResponseRequest.getCgpa());
        save.setSkills(studentResponseRequest.getSkills());
        save.setCollegeName(studentResponseRequest.getCollegeName());
        save.setEducationLevel(studentResponseRequest.getEducationLevel());
        save.setPreferredLocation(studentResponseRequest.getPreferredLocation());
        save.setPreferredState(studentResponseRequest.getPreferredState());

        studentProfileRepository.save(save);

        userRepository.save(user);

        return modelMapper.map(save,StudentProfileResponse.class);
    }

    @Override
    public StudentProfileResponse getStudentProfile(Long profileId) {
        StudentProfile  profile = studentProfileRepository.findById(profileId)
                .orElseThrow(()-> new ResourceNotFoundException("profile" ,"profiel" ,profileId));


        return modelMapper.map(profile,StudentProfileResponse.class);
    }

    // Update existing profile
    @Transactional
    public StudentProfileResponse updateProfile(Long userId, StudentResponseRequest request) {
        StudentProfile profile = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for user id: " ,"us", userId));

        // Partial update
        if (request.getEducationLevel() != null) profile.setEducationLevel(request.getEducationLevel());
        if (request.getCollegeName() != null) profile.setCollegeName(request.getCollegeName());
        if (request.getCgpa() != null) profile.setCgpa(request.getCgpa());
        if (request.getSkills() != null) profile.setSkills(request.getSkills());
        if (request.getInterests() != null) profile.setInterests(request.getInterests());
        if (request.getPreferredLocation() != null) profile.setPreferredLocation(request.getPreferredLocation());
        if (request.getPreferredState() != null) profile.setPreferredState(request.getPreferredState());
        if (request.getBio() != null) profile.setBio(request.getBio());

        StudentProfile updatedProfile = studentProfileRepository.save(profile);
        return modelMapper.map(updatedProfile, StudentProfileResponse.class);
    }


}
