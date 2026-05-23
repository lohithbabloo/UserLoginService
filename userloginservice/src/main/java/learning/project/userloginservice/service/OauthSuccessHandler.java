package learning.project.userloginservice.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.dto.GithubUserInfo;
import learning.project.userloginservice.dto.OAuthUserInfo;

@Service
public class OauthSuccessHandler implements AuthenticationSuccessHandler {
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private OauthUserService oauthUserService;

    public OauthSuccessHandler(OAuth2AuthorizedClientService oAuth2AuthorizedClientService,OauthUserService oauthUserService){
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.oauthUserService = oauthUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        String accessToken = extractAccessToken(auth); 
        OAuthUserInfo oauthUserInfo = prepareRequiredDto(auth.getPrincipal().getAttributes(), "github",accessToken);
        oauthUserService.saveOauthUser(oauthUserInfo, response);
        response.sendRedirect("http://localhost:3000/success");
    }


    public String extractAccessToken(OAuth2AuthenticationToken auth){
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
            auth.getAuthorizedClientRegistrationId(), auth.getPrincipal().getName());
        return client.getAccessToken().getTokenValue();
    }
    private OAuthUserInfo prepareRequiredDto(Map<String,Object> attributes,String OauthProvider,String accessToken){
        if(OauthProvider.equals("github")){
            return new GithubUserInfo(attributes, OauthProvider, accessToken);
        }
        return null;
    }
}