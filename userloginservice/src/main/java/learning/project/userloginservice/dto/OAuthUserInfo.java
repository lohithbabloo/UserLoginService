package learning.project.userloginservice.dto;

public interface OAuthUserInfo {
    String getId();
    String getUserName();
    String getEmailAddress();
    String getOAuthProvider();
    String getAccessToken();
}
