package pl.polsl.repairmanagementdesktop;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager {

    public enum AuthorizedRole{
        MANAGER, WORKER, FAILED
    }

    public AuthorizedRole authorizeForRole(String username, String password){

        if (username.equals("man") && password.equals("man")){
            return AuthorizedRole.MANAGER;
        }
        return AuthorizedRole.FAILED;
    }
}
