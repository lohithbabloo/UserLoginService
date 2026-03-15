package learning.project.userloginservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_ROLES")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_STATUS")
    private String roleStatus; 

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
