package learning.project.userloginservice.operation;

import java.util.Map;
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
import learning.project.userloginservice.model.User;
import learning.project.userloginservice.repository.UserRepository;
import learning.project.userloginservice.service.JwtService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(UserInfoDto userInfoDto) {
        User user = new User(userInfoDto.getUsername(), passwordEncoder.encode(userInfoDto.getPassword()),
                userInfoDto.getEmail(), "Active");
        userRepository.save(user);
        return "User created successfully";
    }

    public ResponseEntity<?> login(UserInfoDto userInfoDto, HttpServletResponse response) throws Exception {
        User user = userRepository.findByUsername(userInfoDto.getUsername());
        if (ObjectUtils.isEmpty(user)) {
            // throw new AuthenticationException("User not found please create a account");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        passwordValidation(userInfoDto.getPassword(), user.getPassword());
        generateTokenAddItToResponse(jwtService.generateToken(userInfoDto.getUsername(), "JWT_TOKEN"), response,
                "JWT_TOKEN", 15 * 60);
        generateTokenAddItToResponse(jwtService.generateToken(userInfoDto.getUsername(), "RETRY_TOKEN"), response,
                "RETRY_TOKEN", 24 * 60 * 60);
        return ResponseEntity.ok(Map.of("message", "Logged In"));
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
                                    .generateToken(jwtService.extractUsername(retryCookie.getValue()), "JWT_TOKEN");
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
}
