package learning.project.userloginservice.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DevSyncCommonCode.class);

    public Map<String,String> getGithubUserName(HttpServletRequest request){
        logger.info("Extracting user ID from the cookie");
        String userId=cookieUtil.extractUserId(request);
        logger.info("Extracting provider access token");
        String accessToken = authProviderRepository.findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(userId, "github", "Active");
        logger.info("Extracting provider user name");
        String githubUserName = oAuthUserRepository.findGithubUserName(userId);
        return Map.of("accessToken",accessToken,"githubUserName",githubUserName);
    }
}
