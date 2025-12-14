package learning.project.userloginservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.operation.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity<>(userService.createUser(userInfoDto), HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfoDto userInfoDto) throws Exception{
        return new ResponseEntity<>(userService.login(userInfoDto), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String apiCheck(){
        return "Hello man";
    }

}
