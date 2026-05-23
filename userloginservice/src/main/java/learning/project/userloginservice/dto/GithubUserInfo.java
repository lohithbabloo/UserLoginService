package learning.project.userloginservice.dto;

import java.util.Map;

import lombok.Data;

@Data
public class GithubUserInfo implements OAuthUserInfo{
    private Map<String,Object> userDetails;
    private String accessToken;
    private String oAuthProvider;

    public GithubUserInfo(Map<String,Object> userDetails,String oAuthProvider,String accessToken){
        this.userDetails = userDetails;
        this.oAuthProvider = oAuthProvider;
        this.accessToken = accessToken;
    }

    @Override
    public String getId() {
        return String.valueOf(userDetails.get("id"));
    }

    @Override
    public String getUserName() {
        return (String) userDetails.get("login");
    }

    @Override
    public String getEmailAddress() {
        return (String) userDetails.get("notification_email");
    }

    @Override
    public String getOAuthProvider() {
        return oAuthProvider;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

}
