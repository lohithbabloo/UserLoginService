package learning.project.userloginservice.operation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.globalException.AuthenticationException;
import learning.project.userloginservice.model.Role;
import learning.project.userloginservice.model.User;
import learning.project.userloginservice.model.UserHasRole;
import learning.project.userloginservice.repository.RoleRepository;
import learning.project.userloginservice.repository.UserHasRoleRepository;
import learning.project.userloginservice.repository.UserRepository;
import learning.project.userloginservice.service.JwtService;
import learning.project.userloginservice.util.Constants;

@Service
public class UserOperation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserHasRoleRepository userHasRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserOperation(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(UserInfoDto userInfoDto) {
        User user = new User(userInfoDto.getUsername(), passwordEncoder.encode(userInfoDto.getPassword()),
                userInfoDto.getEmail(), "Active");
        Role userRole = roleRepository.findByRoleName(Constants.USER);
        UserHasRole userHasRole = new UserHasRole();
        userHasRole.setUser(user);
        userHasRole.setRole(userRole);
        userHasRole.setCreatedAt(LocalDateTime.now());
        userHasRole.setStatus(Constants.STATUS_ACTIVE);
        userHasRoleRepository.save(userHasRole);
        userRepository.save(user);
        return "User created successfully";
    }

    public ResponseEntity<?> login(UserInfoDto userInfoDto, HttpServletResponse response) throws Exception {
        List<UserHasRole> user = userHasRoleRepository.findByUserName(userInfoDto.getUsername());
        List<String> roles = user.stream().map(u -> u.getRole().getRoleName()).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(user)) {
            // throw new AuthenticationException("User not found please create a account");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        passwordValidation(userInfoDto.getPassword(), user.get(0).getUser().getPassword());
        
        generateTokenAddItToResponse(jwtService.generateToken(userInfoDto.getUsername(), generateExtraClaims("JWT_TOKEN", roles)), response,
                "JWT_TOKEN", 15 * 60);
        
        generateTokenAddItToResponse(jwtService.generateToken(userInfoDto.getUsername(), generateExtraClaims("RETRY_TOKEN", roles)), response,
                "RETRY_TOKEN", 24 * 60 * 60);
        return ResponseEntity.ok(Map.of("message", "Logged In"));
    }
    private Map<String,Object> generateExtraClaims(String tokenType, List<String> roles) {
        return Map.of("role", roles,"type", tokenType);
    }
    private static void generateTokenAddItToResponse(String generatedToken, HttpServletResponse response,
            String tokenType, int maxAge) {
        Cookie cookie = new Cookie(tokenType, generatedToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie retryCookie : cookies) {
                if (retryCookie.getName().equals("RETRY_TOKEN")) {
                    if (!ObjectUtils.isEmpty(retryCookie.getValue().toString())) {
                        if (!jwtService.isTokenExpired(retryCookie.getValue().toString())) {
                            String generatedToken = jwtService
                                    .generateToken(jwtService.extractUsername(retryCookie.getValue()), generateExtraClaims("JWT_TOKEN", jwtService.extractRole(retryCookie.getValue().toString())));
                            generateTokenAddItToResponse(generatedToken, response, "JWT_TOKEN", 15 * 60);
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        // check if cookie is a valid one and if yes then generate the jwt token by
        // extracting the username from the token

    }

    public void passwordValidation(String payloadPassword, String dbPassword) throws Exception {
        if (!passwordEncoder.matches(payloadPassword, dbPassword)) {
            throw new AuthenticationException("Invalid Credentials");
        }
    }

    public ResponseEntity<String> userLogout(HttpServletResponse response) {
        generateTokenAddItToResponse(null, response,
                "JWT_TOKEN", 15 * 60);
        generateTokenAddItToResponse(null, response,
                "RETRY_TOKEN", 24 * 60 * 60);
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }


    public List<UserInfoDto> getUserData(){
        List<UserInfoDto> usersData = userRepository.getAllUserData("Active");
        return usersData;
    }
}
