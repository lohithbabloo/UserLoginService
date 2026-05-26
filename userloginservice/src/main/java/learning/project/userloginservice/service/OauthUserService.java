package learning.project.userloginservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import learning.project.userloginservice.dto.OAuthUserInfo;
import learning.project.userloginservice.model.OAuthProvider;
import learning.project.userloginservice.model.OAuthUser;
import learning.project.userloginservice.repository.OAuthProviderRepository;
import learning.project.userloginservice.repository.OAuthUserRepository;
import learning.project.userloginservice.util.Constants;
import learning.project.userloginservice.util.CookieUtil;

@Service
public class OauthUserService {

    @Autowired
    private OAuthProviderRepository providerRepo;

    @Autowired
    private OAuthUserRepository userRepo;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private JwtService jwtService;

    public void saveOauthUser(OAuthUserInfo userDetails,HttpServletResponse response){
        //check if user exists in db based on the internal user id
        OAuthProvider provider = getOauthProviderIfExists(userDetails.getId(), userDetails.getOAuthProvider());
        if(ObjectUtils.isEmpty(provider)){
            //user data
            OAuthUser oAuthUser = new OAuthUser();
            oAuthUser.setGithubUserName(userDetails.getUserName());
            oAuthUser.setEmailAddress(userDetails.getEmailAddress());
            oAuthUser.setUserStatus(Constants.STATUS_ACTIVE);
            oAuthUser.setDevSyncUserOnboarded(false);
            //provider data
            provider = new OAuthProvider();
            provider.setOAuthProvider(userDetails.getOAuthProvider());
            provider.setProviderId(userDetails.getId());
            provider.setAccessToken(userDetails.getAccessToken());
            provider.setTokenStatus(Constants.STATUS_ACTIVE);
            provider.setOAuthUser(oAuthUser);
            oAuthUser.setOAuthProviders(Arrays.asList(provider));
            userRepo.save(oAuthUser);
            providerRepo.save(provider);
            //now generate jwt token and add it in cookies
            cookieUtil.generateTokenAddItToResponse(oAuthUser.getId(), response, "JWT_TOKEN", 15*60);
            cookieUtil.generateTokenAddItToResponse(oAuthUser.getId(), response, "RETRY_TOKEN", 6*60*60);
        }else{
            provider.setAccessToken(userDetails.getAccessToken());
            cookieUtil.generateTokenAddItToResponse(provider.getOAuthUser().getId(), response, "JWT_TOKEN", 15*60);
            cookieUtil.generateTokenAddItToResponse(provider.getOAuthUser().getId(), response, "RETRY_TOKEN", 6*60*60);
            providerRepo.save(provider);
        }
       
    }

    private OAuthProvider getOauthProviderIfExists(String providerId,String provider){
        return providerRepo.findByProviderIdAndOAuthProviderAndTokenStatus(providerId, provider, Constants.STATUS_ACTIVE);
    }
 

    @Transactional
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie retryCookie : cookies) {
                if (retryCookie.getName().equals("RETRY_TOKEN")) {
                    String providerId = getOauthProviderIfExists(jwtService.extractUsername(retryCookie.getValue().toString()),"github").getOAuthUser().getId();
                    if (!ObjectUtils.isEmpty(retryCookie.getValue().toString())) {
                        if (!jwtService.isTokenExpired(retryCookie.getValue().toString())) ;
                            cookieUtil.generateTokenAddItToResponse(providerId, response, "JWT_TOKEN", 15 * 60);
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                    }
                }
            }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}

