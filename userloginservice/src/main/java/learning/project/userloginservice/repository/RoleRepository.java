package learning.project.userloginservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learning.project.userloginservice.model.Role;

public interface RoleRepository extends JpaRepository<Role,String>{
    @Query("select r.roleName from Role r where r.roleStatus ='Active'")
    List<String> findRoleNames();

    Role findByRoleName(String name);
}
