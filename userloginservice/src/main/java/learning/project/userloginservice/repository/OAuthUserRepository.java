package learning.project.userloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.project.userloginservice.model.OAuthUser;

public interface OAuthUserRepository extends JpaRepository<OAuthUser,String>{

}
