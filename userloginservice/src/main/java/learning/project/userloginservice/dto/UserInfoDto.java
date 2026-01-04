package learning.project.userloginservice.dto;

import lombok.Data;

@Data
public class UserInfoDto {

    private String username;
    private String email;
    private String password;
    

    public UserInfoDto(String username,String email,String password){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
