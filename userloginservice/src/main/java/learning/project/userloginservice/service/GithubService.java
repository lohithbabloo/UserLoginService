package learning.project.userloginservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import learning.project.userloginservice.dto.CommitDto;
import learning.project.userloginservice.dto.GithubRepoDto;

@Service
public class GithubService {
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;


    @Value("${services.authify.githuburl}")
    private String githubBaseUrl;

    @Autowired
    private GithubClientApi githubClientApi;

    private WebClient webClient;

    private GithubService(WebClient webClient,OAuth2AuthorizedClientService oAuth2AuthorizedClientService){
        this.webClient = webClient;
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    public String extractAccessToken(Authentication authentication){
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
            auth.getAuthorizedClientRegistrationId(), auth.getPrincipal().getName());
        return client.getAccessToken().getTokenValue();
    }

    public String getUserInfoFromGithub(String accessToken){
        return githubClientApi.get(githubBaseUrl+"/user", accessToken);
    }

    public List<GithubRepoDto> getUserRepoData(String accessToken){
        String url = githubBaseUrl+"/user/repos";
        List<GithubRepoDto> userRepoData = webClient.get().uri(url)
                                        .headers(headers -> headers.setBearerAuth(accessToken))
                                        .retrieve().bodyToFlux(GithubRepoDto.class).collectList().block();    
        return userRepoData;
    }

    public List<CommitDto> getUserCommitData(String githubUserName,String repoName,String accessToken){
        String url = githubBaseUrl+"/repos/"+githubUserName+"/"+repoName+"/"+"commits?per_page=10";
        List<CommitDto> commitDataOnRepo = webClient.get().uri(url).headers(headers->headers.setBearerAuth(accessToken)).retrieve().bodyToFlux(CommitDto.class).collectList().block();
        return commitDataOnRepo;
    }


    public void getSpecificUserCommitData(String githubUserName,String repoName,String accessToken,String sha,List<CommitDto> commitFileChanges){
        String url = githubBaseUrl+"/repos/"+githubUserName+"/"+repoName+"/"+"commits/"+sha;
        CommitDto commitDataOnRepo = webClient.get().uri(url).headers(headers->headers.setBearerAuth(accessToken)).retrieve().bodyToMono(CommitDto.class).block();
        commitFileChanges.add(commitDataOnRepo);
    } 

    public Map<String,Long> getRepositoryLanguageDensity(String githubUserName,String repoName,String accessToken){
        String url = githubBaseUrl+"/repos/"+githubUserName+"/"+repoName+"/"+"languages";
        Map<String,Long> languageDensity = webClient.get()
        .uri(url)
        .headers(headers -> headers.setBearerAuth(accessToken))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Map<String, Long>>() {})
        .block();
        return languageDensity;
    }
}
