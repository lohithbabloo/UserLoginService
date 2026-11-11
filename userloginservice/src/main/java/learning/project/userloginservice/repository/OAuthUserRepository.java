package learning.project.userloginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import learning.project.userloginservice.dto.UserDto;
import learning.project.userloginservice.model.OAuthUser;

public interface OAuthUserRepository extends JpaRepository<OAuthUser,String>{
    @Query("select u from OAuthUser u where u.id=?1 and u.userStatus=?2")
    Optional<OAuthUser> findByIdAndStatus(String id,String status);

    @Query("select new learning.project.userloginservice.dto.UserDto(u.userName,u.githubUserName,u.emailAddress,u.devSyncUserOnboarded) from OAuthUser u where u.id=?1 and u.userStatus=?2")
    UserDto findByIdAndUserStatus(String id,String status);

    @Modifying
    @Transactional
    @Query("update OAuthUser u set u.userName=?1 , u.devSyncUserOnboarded=?3 , u.emailAddress=?2 where u.githubUserName=?4")
    void updateUserData(String userName,String email,Boolean onBoarded,String githubUserName);

    @Query("select u.githubUserName from OAuthUser u WHERE u.id = ?1 ")
    String findGithubUserName(String id);
}
