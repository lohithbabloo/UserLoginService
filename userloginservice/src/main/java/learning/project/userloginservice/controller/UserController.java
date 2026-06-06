package learning.project.userloginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.UserDto;
import learning.project.userloginservice.operation.UserOperation;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserOperation userOperation;
    @GetMapping("/me")
    public ResponseEntity<UserDto> getAuthenticatedUserData(HttpServletRequest request){
        return new ResponseEntity<>(userOperation.getLoggedInUserData(request),HttpStatus.OK);
    }
}
