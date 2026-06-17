package learning.project.userloginservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.GithubRepoDto;
import learning.project.userloginservice.operation.GithubOperation;

@RestController
@RequestMapping("/api/v1/github")
public class GithubController {
    
    @Autowired
    private GithubOperation githubOperation;

    
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request){
        return githubOperation.getUserInfoFromGithub(request);
    }

    @GetMapping("/repos")
    public ResponseEntity<List<GithubRepoDto>> getUserRepositoriesData(HttpServletRequest request){
        return new ResponseEntity<>(githubOperation.getUserRepositoryData(request),HttpStatus.OK);
    }

    @GetMapping("/{repoName}/commits")
    public ResponseEntity<?> getRepoCommitDto(HttpServletRequest request,@PathVariable(required = true) String repoName){
        return githubOperation.getRepoCommitDetails(request, repoName);
    }


}
