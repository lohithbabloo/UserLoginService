package learning.project.userloginservice.util;

import org.springframework.http.HttpHeaders;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learning.project.userloginservice.globalException.UnAuthorizedException;
import learning.project.userloginservice.service.JwtService;

@Component
public class CookieUtil {
    private JwtService jwtService;
    public CookieUtil(JwtService jwtService){
        this.jwtService = jwtService;
    }
    public static String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void generateTokenAddItToResponse(String userId, HttpServletResponse response,
            String tokenType, Duration maxAge) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type",tokenType);
        String generatedToken = jwtService.generateToken(userId, claims,maxAge);
        ResponseCookie cookie = ResponseCookie.from(tokenType,generatedToken).httpOnly(true).sameSite("Lax").maxAge(maxAge).secure(false).path("/").build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());;
    }

    public String extractUserId(HttpServletRequest request){
        String jwtToken = CookieUtil.getCookie("JWT_TOKEN", request);
        boolean tokenValidation=jwtService.validateToken(jwtToken);
        if(!tokenValidation){
            throw new UnAuthorizedException("User doesnt have access to perform this operation");
        }
        String userId = jwtService.extractUsername(jwtToken);
        return userId;
    }

}
