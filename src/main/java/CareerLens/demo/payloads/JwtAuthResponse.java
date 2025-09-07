package CareerLens.demo.payloads;

import CareerLens.demo.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;

    public JwtAuthResponse(String jwt, Long id, String email, UserRole role) {

        this.token =jwt;
        this.email =email;
        this.id = id;
        this.role = role;


    }


}