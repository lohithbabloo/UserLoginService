package learning.project.userloginservice.operation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.repository.OAuthProviderRepository;
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
    
    public ResponseEntity<?> getUserInfoFromGithub(HttpServletRequest request){
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(jwtToken);
        String accessToken = oAuthProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String url = "https://api.github.com/user";
        String userInfo = githubClientApi.get(url, accessToken);
        return ResponseEntity.ok(userInfo);
    }




}
