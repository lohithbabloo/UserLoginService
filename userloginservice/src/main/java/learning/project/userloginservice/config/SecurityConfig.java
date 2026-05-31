package learning.project.userloginservice.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import learning.project.userloginservice.service.JwtAuthCustomFilter;
import learning.project.userloginservice.service.OauthFailureHandler;
import learning.project.userloginservice.service.OauthSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
    private OauthSuccessHandler successHandler;
    private OauthFailureHandler failureHandler;
    public SecurityConfig(OauthSuccessHandler successHandler,OauthFailureHandler failureHandler){
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthCustomFilter customFilter) {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/login/oauth2/**")
            ).oauth2Login(oauth -> oauth.successHandler(successHandler).failureHandler(failureHandler))
            .authorizeHttpRequests(auth -> auth    
                .requestMatchers("/actuator/**").permitAll() 
                .requestMatchers("/oauth2/**", "/login/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        
        cors.setAllowedOrigins(Arrays.asList(
            "https://authify.duckdns.org","http://localhost:3000"
        ));
        cors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("No default users - use /auth/login");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    // @Bean
    // public RoleHierarchy roleHierarchy() {
    //     String hierarchy = "ROLE_Admin > Role_Manager > ROLE_User";
    //     return RoleHierarchyImpl.fromHierarchy(hierarchy);
    // }

    // @Bean 
    // public GrantedAuthoritiesMapper grantedAuthoritiesMapper(RoleHierarchy roleHierarchy) {
    //     return authorities -> roleHierarchy.getReachableGrantedAuthorities(authorities);
    // }

    
}


