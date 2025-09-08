package CareerLens.demo.services;

import CareerLens.demo.exceptions.ResourceNotFoundException;
import CareerLens.demo.payloads.userDTOs.UserDTO;
import CareerLens.demo.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements  UserServices{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDTO getUserById(Long userId) {
        CareerLens.demo.model.User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

        return modelMapper.map(user, UserDTO.class);
    }

}
