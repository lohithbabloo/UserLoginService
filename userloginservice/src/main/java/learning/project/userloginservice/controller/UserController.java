package learning.project.userloginservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.operation.UserOperation;


@RestController
@RequestMapping("/internal/api")
@EnableMethodSecurity
public class UserController {

    @Autowired
    private UserOperation userOperation;
    //need to write base 
    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<List<UserInfoDto>> getAllUsersData(){
        return new ResponseEntity<>(userOperation.getUserData(),HttpStatus.OK);
    }
}
