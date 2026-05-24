package learning.project.userloginservice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            String tokenType, int maxAge) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type",tokenType);
        String generatedToken = jwtService.generateToken(userId, claims);
        Cookie cookie = new Cookie(tokenType, generatedToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
