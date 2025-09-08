package CareerLens.demo.controller;

import CareerLens.demo.payloads.ApiResponse;
import CareerLens.demo.payloads.JwtAuthResponse;
import CareerLens.demo.payloads.userDTOs.LoginRequest;
import CareerLens.demo.payloads.userDTOs.UserRegistrationRequest;
import CareerLens.demo.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private  AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody
                                                    UserRegistrationRequest userRegistrationRequest){
       authService.registerUser(userRegistrationRequest);
       return ResponseEntity.status(HttpStatus.OK).body(
               new ApiResponse(true,"User register successfully.")
       );

    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        JwtAuthResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);

    }


}
