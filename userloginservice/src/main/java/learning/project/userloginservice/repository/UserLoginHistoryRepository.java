package learning.project.userloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.project.userloginservice.model.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, String>{
    
}
