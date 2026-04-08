package learning.project.userloginservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.util.CookieUtil;

@Component
public class JwtAuthCustomFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final GrantedAuthoritiesMapper authoritiesMapper;

    public JwtAuthCustomFilter(JwtService jwtService, GrantedAuthoritiesMapper authoritiesMapper) {
        this.jwtService = jwtService;
        this.authoritiesMapper = authoritiesMapper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String cookie = CookieUtil.getCookie("JWT_TOKEN", request);
        if(Objects.nonNull(cookie) && !jwtService.isTokenExpired(cookie)) {
            String username = jwtService.extractUsername(cookie);
            List<String> roles = jwtService.extractRole(cookie);
            var authorities = roles.stream().map(x-> new SimpleGrantedAuthority("ROLE_"+x))
                .collect(Collectors.toList());
            var mappedAuthorities = authoritiesMapper.mapAuthorities(authorities);
            UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(username, null, mappedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
        
    }
    
}
