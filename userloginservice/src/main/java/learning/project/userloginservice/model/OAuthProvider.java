package learning.project.userloginservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OAUTH_PROVIDER")
@Data
public class OAuthProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "OAUTH_PROVIDER")
    private String oAuthProvider;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDateTime = LocalDateTime.now();

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "TOKEN_STATUS")
    private String tokenStatus;

    @ManyToOne
    @JoinColumn(name = "OAUTH_USER_ID")
    private OAuthUser oAuthUser;;
}
