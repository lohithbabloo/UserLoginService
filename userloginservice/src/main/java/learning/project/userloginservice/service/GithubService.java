package learning.project.userloginservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public String extractAccessToke(Authentication authentication){
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
            auth.getAuthorizedClientRegistrationId(), auth.getPrincipal().getName());
            System.out.println(client);
            System.out.println(client.getAccessToken().getTokenValue());
        return client.getAccessToken().getTokenValue();
    }
}
