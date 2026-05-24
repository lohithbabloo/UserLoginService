package learning.project.userloginservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OAUTH_USER")
@Getter
@Setter
public class OAuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "GITHUB_USERNAME")
    private String githubUserName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDateTime = LocalDateTime.now();

    @Column(name = "DEVSYNC_USER_ONBOARDED")
    private Boolean devSyncUserOnboarded;

    @Column(name = "USER_STATUS")
    private String userStatus;

    @OneToMany(mappedBy = "oAuthUser",cascade = CascadeType.ALL)
    private List<OAuthProvider> oAuthProviders;
}
