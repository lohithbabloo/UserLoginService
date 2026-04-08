package learning.project.userloginservice.util;

import java.util.Collections;
import java.util.List;


public class Constants {
    public static final String MANAGER = "Manager";
    public static final String ADMIN = "Admin";
    public static final String USER = "User";
    public static final List<String> ROLES_IN_APPLICATION = Collections.unmodifiableList(List.of(MANAGER, ADMIN, USER));
    public static final String STATUS_ACTIVE = "Active";
}
