package learning.project.userloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.project.userloginservice.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
