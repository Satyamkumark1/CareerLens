package CareerLens.demo.controller;
import CareerLens.demo.payloads.StudentProfileResponse;
import CareerLens.demo.payloads.StudentResponseRequest;
import CareerLens.demo.security.UserPrincipal;
import CareerLens.demo.services.StudentProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;


    @PostMapping("/add")
    public ResponseEntity<StudentProfileResponse> creatStudentProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody StudentResponseRequest studentResponseRequest
    ) {
        StudentProfileResponse profileResponse = studentProfileService
                .createProfile(studentResponseRequest,currentUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponse);
    }


    @GetMapping("/profile/{profileId}")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable  Long profileId
    ) {
        StudentProfileResponse profileResponse = studentProfileService
                .getStudentProfile(profileId);

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentProfileResponse> updateStudentProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody StudentResponseRequest request
            ) {
        StudentProfileResponse profile = studentProfileService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(profile);
    }

}
