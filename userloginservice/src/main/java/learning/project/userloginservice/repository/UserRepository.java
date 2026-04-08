package learning.project.userloginservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import learning.project.userloginservice.dto.UserInfoDto;
import learning.project.userloginservice.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    @Query("select new learning.project.userloginservice.dto.UserInfoDto(u.username,u.email) from User u where u.userStatus = :status")
    List<UserInfoDto> getAllUserData(@Param("status") String status);


    @Query("select new learning.project.userloginservice.dto.UserInfoDto(u.username,u.email) from User u where u.username = :username and  u.userStatus = :status")
    UserInfoDto getUserData(@Param("username") String username,@Param("status") String status);
}
