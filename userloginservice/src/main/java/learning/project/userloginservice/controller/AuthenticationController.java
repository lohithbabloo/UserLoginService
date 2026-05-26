package learning.project.userloginservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.service.OauthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthenticationController {

    @Autowired
    private OauthUserService oauthUserService;
    

    @GetMapping("/refreshToken")
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        return oauthUserService.getRefreshToken(request, response);
    }

    
}
