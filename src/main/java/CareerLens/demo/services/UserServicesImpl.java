package CareerLens.demo.services;

import CareerLens.demo.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements  UserServices{

    @Autowired
    private UserRepository userRepositery;

    @Override
    public User adduser(User user) {


        return null;
    }





}
