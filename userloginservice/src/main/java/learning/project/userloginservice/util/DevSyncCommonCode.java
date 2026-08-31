package learning.project.userloginservice.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.repository.OAuthProviderRepository;
import learning.project.userloginservice.repository.OAuthUserRepository;

@Component
public class DevSyncCommonCode {
    @Autowired
    CookieUtil cookieUtil;
    
    @Autowired
    OAuthUserRepository oAuthUserRepository;

    @Autowired
    OAuthProviderRepository authProviderRepository;

    public Map<String,String> getGithubUserName(HttpServletRequest request){
        String userId=cookieUtil.extractUserId(request);
        String accessToken = authProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        String githubUserName = oAuthUserRepository.findGithubUserName(userId);
        return Map.of("accessToken",accessToken,"githubUserName",githubUserName);
    }
}
