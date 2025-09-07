package CareerLens.demo.services;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServices  {
    User adduser(User user);
}
