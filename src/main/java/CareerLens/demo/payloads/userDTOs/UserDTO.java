package CareerLens.demo.payloads.userDTOs;


import CareerLens.demo.model.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private Boolean isFirstGenLearner;
    private Boolean accountVerified;
    private Boolean hasProfile; // Useful for frontend to know if profile exists
    private LocalDateTime createdAt;
}