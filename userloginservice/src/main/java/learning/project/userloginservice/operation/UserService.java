package learning.project.userloginservice.operation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.model.User;
import learning.project.userloginservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public String createUser(UserInfoDto userInfoDto) {
        User user = new User();
        user.setUsername(userInfoDto.getUsername());
        user.setEmail(userInfoDto.getEmail());
        user.setPassword(userInfoDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserStatus("ACTIVE");
        userRepository.save(user);
        return "User created successfully";
    }
}
