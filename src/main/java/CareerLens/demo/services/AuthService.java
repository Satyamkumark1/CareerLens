package CareerLens.demo.services;

import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.model.User;
import CareerLens.demo.model.UserRole;
import CareerLens.demo.payloads.JwtAuthResponse;
import CareerLens.demo.payloads.LoginRequest;
import CareerLens.demo.payloads.UserRegistrationRequest;
import CareerLens.demo.repository.UserRepository;
import CareerLens.demo.security.JwtTokenProvider;
import CareerLens.demo.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public User registerUser(@Valid UserRegistrationRequest userRegistrationRequest) {
       if ( userRepository.existsByEmail(userRegistrationRequest.getEmail())){
           throw new ResourceNotFoundException("email", userRegistrationRequest.getEmail(), " already exist");
       }
       User user = new User();
       user.setEmail(userRegistrationRequest.getEmail());
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setRole(UserRole.STUDENT);
        user.setLastName(userRegistrationRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setFirstGenLearner(userRegistrationRequest.getIsFirstGenLearner());
        user.setAccountVerified(false);
        user.setAccountVerified(Boolean.parseBoolean(UUID.randomUUID().toString()));

        User savedUser = userRepository.save(user);

        return savedUser;

    }


    public JwtAuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return new JwtAuthResponse(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getEmail(),
//                userPrincipal.getFirstName(),
//                userPrincipal.getLastName(),
                UserRole.valueOf(userPrincipal.getAuthorities().iterator().next()
                        .getAuthority().replace("ROLE_", ""))
        );
    }





}
