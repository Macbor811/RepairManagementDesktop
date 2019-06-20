package pl.polsl.repairmanagementdesktop;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager {

    public enum AuthorizedRole{
        MANAGER, WORKER, FAILED, ADMIN
    }

    public AuthorizedRole authorizeForRole(String username, String password){

        if (username.equals("man")){
            return AuthorizedRole.MANAGER;
        }
        else if (username.equals("admin")){
            return AuthorizedRole.ADMIN;
        }else if (username.equals("work")){
            return AuthorizedRole.WORKER;
        }
        return AuthorizedRole.FAILED;
    }
}
