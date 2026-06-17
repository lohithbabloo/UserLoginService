package learning.project.userloginservice.operation;


import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.CommitDto;
import learning.project.userloginservice.dto.GithubRepoDto;
import learning.project.userloginservice.repository.OAuthProviderRepository;
import learning.project.userloginservice.repository.OAuthUserRepository;
import learning.project.userloginservice.service.GithubClientApi;
import learning.project.userloginservice.service.JwtService;
import learning.project.userloginservice.util.CookieUtil;

@Service
public class GithubOperation {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private OAuthProviderRepository oAuthProviderRepository;

    @Autowired
    private GithubClientApi githubClientApi;

    @Autowired
    private OAuthUserRepository authUserRepository;

    private WebClient webClient;

    public GithubOperation(WebClient webClient){
        this.webClient = webClient;
    }

    @Autowired
    private CookieUtil cookieUtil;
    
    public ResponseEntity<?> getUserInfoFromGithub(HttpServletRequest request){
        String jwtToken = cookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String url = "https://api.github.com/user";
        String userInfo = githubClientApi.get(url, accessToken);
        return ResponseEntity.ok(userInfo);
    }

    public List<GithubRepoDto> getUserRepositoryData(HttpServletRequest request){
        String jwtToken = cookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String url = "https://api.github.com/user/repos";
        List<GithubRepoDto> userRepoData = webClient.get().uri(url)
                                        .headers(headers -> headers.setBearerAuth(accessToken))
                                        .retrieve().bodyToFlux(GithubRepoDto.class).collectList().block();    
        return userRepoData;
    }

    public ResponseEntity<?> getRepoCommitDetails(HttpServletRequest request,String repoName){
        String jwtToken = cookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String githubUserName = authUserRepository.findGithubUserName(userId);
        String url = "https://api.github.com/repos/"+githubUserName+"/"+repoName+"/"+"commits";
        String response = githubClientApi.get("https://api.github.com/repos/"+githubUserName+"/"+repoName+"/"+"commits", accessToken);
        List<CommitDto> commitDataOnRepo = webClient.get().uri(url).headers(headers->headers.setBearerAuth(accessToken)).retrieve().bodyToFlux(CommitDto.class).collectList().block();
        return new ResponseEntity<>(commitDataOnRepo,HttpStatus.OK);
    }
}
