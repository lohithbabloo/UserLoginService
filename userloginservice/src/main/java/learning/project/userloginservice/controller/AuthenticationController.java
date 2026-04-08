package learning.project.userloginservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.operation.UserOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthenticationController {

    @Autowired
    private UserOperation userOperation;

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity<>(userOperation.createUser(userInfoDto), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfoDto userInfoDto, HttpServletResponse response,HttpServletRequest request)
            throws Exception {
        return userOperation.login(userInfoDto, response, request);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        return userOperation.getRefreshToken(request, response);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        return userOperation.userLogout(response);
    }
}
