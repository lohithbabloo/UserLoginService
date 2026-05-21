package learning.project.userloginservice.service;

import java.io.IOException;


import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OauthSuccessHandler implements AuthenticationSuccessHandler {
    private GithubService githubService;
    public OauthSuccessHandler(GithubService githubService){
        this.githubService = githubService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // get the token out and generate save the token in the database
        githubService.extractAccessToke(authentication);
        response.sendRedirect("http://localhost:3000/success");
    }

}
