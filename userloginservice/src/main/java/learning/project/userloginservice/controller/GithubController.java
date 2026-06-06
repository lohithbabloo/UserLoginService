package learning.project.userloginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.operation.GithubOperation;

@RestController
@RequestMapping("/api/v1/github")
public class GithubController {
    
    @Autowired
    private GithubOperation githubOperation;

    
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(Authentication authentication,HttpServletRequest request){
        return githubOperation.getUserInfoFromGithub(request);
    }



}
