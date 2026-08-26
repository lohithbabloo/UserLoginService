package learning.project.userloginservice.operation;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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

    
    public ResponseEntity<?> getUserInfoFromGithub(HttpServletRequest request){
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String url = "https://api.github.com/user";
        String userInfo = githubClientApi.get(url, accessToken);
        return ResponseEntity.ok(userInfo);
    }

    public List<GithubRepoDto> getUserRepositoryData(HttpServletRequest request){
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String url = "https://api.github.com/user/repos";
        List<GithubRepoDto> userRepoData = webClient.get().uri(url)
                                        .headers(headers -> headers.setBearerAuth(accessToken))
                                        .retrieve().bodyToFlux(GithubRepoDto.class).collectList().block();    
        return userRepoData;
    }

    public List<CommitDto> getRepoCommitDetails(HttpServletRequest request,String repoName){
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String githubUserName = authUserRepository.findGithubUserName(userId);
        String url = "https://api.github.com/repos/"+githubUserName+"/"+repoName+"/"+"commits?per_page=5";
        List<CommitDto> commitDataOnRepo = webClient.get().uri(url).headers(headers->headers.setBearerAuth(accessToken)).retrieve().bodyToFlux(CommitDto.class).collectList().block();
        return commitDataOnRepo;
    }

    public List<CommitDto> getcommitData(HttpServletRequest request,String repoName,List<String> shaValue){
        List<CommitDto> commitFileChanges = new ArrayList<>();
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String githubUserName = authUserRepository.findGithubUserName(userId);
        if(!ObjectUtils.isEmpty(shaValue)) {
            for(String sha: shaValue){
                String url = "https://api.github.com/repos/"+githubUserName+"/"+repoName+"/"+"commits/"+sha;
                CommitDto commitData = webClient.get().uri(url).headers(headers->headers.setBearerAuth(accessToken)).retrieve().bodyToMono(CommitDto.class).block();
                commitFileChanges.add(commitData);
            }
        }
        return commitFileChanges;
    }
}
