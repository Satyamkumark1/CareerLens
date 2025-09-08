package CareerLens.demo.services;


import CareerLens.demo.payloads.userDTOs.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserServices  {

    UserDTO getUserById(Long userId);
}
