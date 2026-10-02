package learning.project.userloginservice.operation;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.UserDto;
import learning.project.userloginservice.model.OAuthUser;
import learning.project.userloginservice.repository.OAuthUserRepository;
import learning.project.userloginservice.service.JwtService;
import learning.project.userloginservice.util.Constants;
import learning.project.userloginservice.util.CookieUtil;

@Service
public class UserOperation {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    
    public UserDto getLoggedInUserData(HttpServletRequest request) throws Exception{
        String cookie = CookieUtil.getCookie("JWT_TOKEN", request);
        if(ObjectUtils.isEmpty(cookie)){
            throw new Exception("User not authenticated");
        }
        String userId = jwtService.extractUsername(cookie);
        UserDto userDto = oAuthUserRepository.findByIdAndUserStatus(userId, Constants.STATUS_ACTIVE);
        return userDto;
    }

    public UserDto updateUserOnBoardingData(UserDto userDto,HttpServletRequest request) throws Exception{
        String cookie = CookieUtil.getCookie("JWT_TOKEN", request);
        if(ObjectUtils.isEmpty(cookie)){
            throw new Exception("User not authenticated");
        }
        String userId = jwtService.extractUsername(cookie);
        //get user details and update the data
        Optional<OAuthUser> authUser = oAuthUserRepository.findByIdAndStatus(userId,Constants.STATUS_ACTIVE);
        if(authUser.isPresent()){
            
            authUser.get().setDevSyncUserOnboarded(true);
            if(!ObjectUtils.isEmpty(userDto.getEmail())){
                authUser.get().setEmailAddress(userDto.getEmail());
            }
            oAuthUserRepository.save(authUser.get());
        }

        return userDto;
    }
}
