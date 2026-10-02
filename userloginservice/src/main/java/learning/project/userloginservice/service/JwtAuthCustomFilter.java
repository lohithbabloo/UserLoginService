package learning.project.userloginservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.model.OAuthUser;
import learning.project.userloginservice.repository.OAuthUserRepository;
import learning.project.userloginservice.util.CookieUtil;

@Component
public class JwtAuthCustomFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final OAuthUserRepository oAuthUserRepository;

    public JwtAuthCustomFilter(JwtService jwtService,OAuthUserRepository oAuthUserRepository) {
        this.jwtService = jwtService;
        this.oAuthUserRepository = oAuthUserRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = CookieUtil.getCookie("JWT_TOKEN", request);

        if(ObjectUtils.isEmpty(token)){
            filterChain.doFilter(request, response);
            return;
        }
        
        Claims claims = jwtService.parseTokenAndRequireType(token, "JWT_TOKEN");
        String userId = claims.getSubject();

        //fetch user details from the db
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findById(userId);
        
        if(!oAuthUser.isPresent()){
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(oAuthUser, null,List.of(new SimpleGrantedAuthority("ROLE_USER")));

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
        
        filterChain.doFilter(request, response);
    }


    
}
