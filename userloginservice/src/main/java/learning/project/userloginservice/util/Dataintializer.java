package learning.project.userloginservice.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import learning.project.userloginservice.model.Role;
import learning.project.userloginservice.model.User;
import learning.project.userloginservice.model.UserHasRole;
import learning.project.userloginservice.repository.RoleRepository;
import learning.project.userloginservice.repository.UserHasRoleRepository;
import learning.project.userloginservice.repository.UserRepository;

@Component
public class Dataintializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserHasRoleRepository userHasRoleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Data intializer is running");
        // check if roles are present in the db else create
        List<String> roleNames = roleRepository.findRoleNames();
        List<Role> rolesToBeSaved = new ArrayList<>();
        List<String> missingRolesInDb = Constants.ROLES_IN_APPLICATION.stream().filter(y->!roleNames.contains(y)).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(missingRolesInDb)){
            for(String roleName: missingRolesInDb){
                // Create the role
                Role newRole = new Role();
                newRole.setRoleName(roleName);
                newRole.setRoleStatus(Constants.STATUS_ACTIVE);
                newRole.setCreatedAt(LocalDateTime.now());
                rolesToBeSaved.add(newRole);
            }}
        roleRepository.saveAll(rolesToBeSaved);
        // get if any admin user is in the system if none create a default user
        int adminUsersInSystem = userHasRoleRepository.findCountByRole(Constants.ADMIN);
        Role adminRole = roleRepository.findByRoleName(Constants.ADMIN);
        if(adminUsersInSystem ==0){
            //create a admin user
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUserStatus(Constants.STATUS_ACTIVE);
            user.setEmail("admin@system.com");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            UserHasRole userHasRole = new UserHasRole();
            userHasRole.setUser(user);
            userHasRole.setRole(adminRole);
            userHasRole.setStatus(Constants.STATUS_ACTIVE);
            userHasRole.setCreatedAt(LocalDateTime.now());
            userHasRoleRepository.save(userHasRole);
        }
    }
}


