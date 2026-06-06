package learning.project.userloginservice.operation;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.UserDto;
import learning.project.userloginservice.repository.OAuthUserRepository;
import learning.project.userloginservice.service.JwtService;
import learning.project.userloginservice.util.Constants;
import learning.project.userloginservice.util.CookieUtil;

@Service
public class UserOperation {

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    
    public UserDto getLoggedInUserData(HttpServletRequest request){
        String cookie = cookieUtil.getCookie("JWT_TOKEN", request);
        String userId = jwtService.extractUsername(cookie);
        UserDto userDto = oAuthUserRepository.findByIdAndStatus(userId, Constants.STATUS_ACTIVE);
        return userDto;
    }
}
