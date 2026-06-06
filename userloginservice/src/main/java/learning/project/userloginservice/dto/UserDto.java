package learning.project.userloginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String githubUserName;
    private String email;
    private boolean userOnboarded;
}
