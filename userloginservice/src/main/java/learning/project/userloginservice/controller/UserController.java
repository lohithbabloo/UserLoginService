package learning.project.userloginservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.operation.UserService;
import learning.project.userloginservice.service.JwtService;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity<>(userService.createUser(userInfoDto), HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfoDto userInfoDto, HttpServletResponse response) throws Exception{
        return new ResponseEntity<>(userService.login(userInfoDto ,response), HttpStatus.OK);
    }

    @GetMapping("/hello/jwtToken")
    public String apiCheck(@RequestParam(required = false) String jwtToken,HttpServletResponse response) throws Exception{
            UserInfoDto dto = new UserInfoDto();
        dto.setUsername("Lohith");
        dto.setPassword("pass");
        if(Objects.nonNull(jwtToken) &&jwtService.validateToken(jwtToken, dto)){
            return "validated";
        }else{
            userService.login(dto, response);
            return "generated";
        }
    }

}
