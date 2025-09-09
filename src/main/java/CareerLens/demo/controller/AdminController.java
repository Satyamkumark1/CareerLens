package CareerLens.demo.controller;

import CareerLens.demo.model.User;
import CareerLens.demo.model.UserRole;
import CareerLens.demo.payloads.userDTOs.CreateAdminRequest;
import CareerLens.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    // Get all admins
    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        List<User> admins = userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .toList();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // Delete a user by id
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // Create a new admin user
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin with this email already exists");
        }
        User admin = new User();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setRole(UserRole.ADMIN);
        admin.setAccountVerified(true);
        userRepository.save(admin);
        return ResponseEntity.ok("Admin created successfully");
    }


    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
