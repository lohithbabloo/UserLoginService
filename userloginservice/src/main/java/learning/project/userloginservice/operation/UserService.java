package learning.project.userloginservice.operation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.model.User;
import learning.project.userloginservice.repository.UserRepository;
import learning.project.userloginservice.service.JwtService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    
    public String createUser(UserInfoDto userInfoDto) {
        User user = new User();
        user.setUsername(userInfoDto.getUsername());
        user.setEmail(userInfoDto.getEmail());
        user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserStatus("ACTIVE");
        userRepository.save(user);
        return "User created successfully";
    }

    public String login(UserInfoDto userInfoDto) throws Exception{
        User user = userRepository.findByUsername(userInfoDto.getUsername());
        if(ObjectUtils.isEmpty(user)){
            throw new Exception("User not found please create a account");
        }
        passwordValidation(userInfoDto.getPassword(),user.getPassword());
        return jwtService.generateToken(userInfoDto.getUsername());
        // return "User logged in successfully";
    }

    public void passwordValidation(String payloadPassword,String dbPassword) throws Exception{
        if(!passwordEncoder.matches(payloadPassword, dbPassword)){
            throw new Exception("Wrong Password");
        }
    }
}
