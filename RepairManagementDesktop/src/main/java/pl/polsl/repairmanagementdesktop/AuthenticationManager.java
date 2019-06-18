package pl.polsl.repairmanagementdesktop;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager {

    public enum AuthorizedRole{
        MANAGER, WORKER, FAILED, ADMIN
    }

    public AuthorizedRole authorizeForRole(String username, String password){

        if (username.equals("man") && password.equals("man")){
            return AuthorizedRole.MANAGER;
        }
        else if (username.equals("admin") && password.equals("admin")){
            return AuthorizedRole.ADMIN;
        }else if (username.equals("work") && password.equals("work")){
            return AuthorizedRole.WORKER;
        }
        return AuthorizedRole.FAILED;
    }
}
