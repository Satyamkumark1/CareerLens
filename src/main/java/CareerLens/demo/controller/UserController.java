package CareerLens.demo.controller;

import CareerLens.demo.payloads.UserDTO;
import CareerLens.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServices userServices;


    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){

     UserDTO userDTO =  userServices.getUserById(userId);
     return new ResponseEntity<>(userDTO,HttpStatus.OK);


    }
}
