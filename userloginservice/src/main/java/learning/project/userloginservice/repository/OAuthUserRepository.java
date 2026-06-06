package learning.project.userloginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learning.project.userloginservice.dto.UserDto;
import learning.project.userloginservice.model.OAuthUser;

public interface OAuthUserRepository extends JpaRepository<OAuthUser,String>{
    Optional<OAuthUser> findById(String id);

    @Query("select new learning.project.userloginservice.dto.UserDto(u.userName,u.githubUserName,u.emailAddress,u.devSyncUserOnboarded) from OAuthUser u where u.id=?1 and u.userStatus=?2")
    UserDto findByIdAndStatus(String id,String status);
}
