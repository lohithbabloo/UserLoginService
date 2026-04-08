package learning.project.userloginservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "USER_LOGIN_HISTORY")
@Entity
@Data
public class UserLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "LOGIN_TIME")
    private LocalDateTime loginTime;

    @Column(name = "STATUS")
    private String loginStatus;

    @Column(name = "USER_DEVICE")
    private String userDevice;
    
}
