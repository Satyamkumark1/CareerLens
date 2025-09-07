package CareerLens.demo.services;

import CareerLens.demo.model.StudentProfile;
import CareerLens.demo.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileServiceImpl implements  StudentProfileService{

    @Autowired
    private StudentProfileRepository studentProfileRepository;



    @Override
    public void addStudent(StudentProfile studentProfile) {




    }
}
