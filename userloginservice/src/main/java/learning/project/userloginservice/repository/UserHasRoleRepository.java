package learning.project.userloginservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import learning.project.userloginservice.model.UserHasRole;

public interface UserHasRoleRepository extends JpaRepository<UserHasRole, String> {
    @Query("select count(*) from UserHasRole uhr left join Role r on uhr.role.id = r.id and r.roleStatus='Active' and uhr.status='Active'")
    int findCountByRole(String role);
    @Query("select uhr from UserHasRole uhr left join Role r on uhr.role.id = r.id and r.roleStatus='Active' and uhr.status='Active' left join User u on uhr.user.id = u.id where u.username=:userName")
    List<UserHasRole> findByUserName(String userName);
}
