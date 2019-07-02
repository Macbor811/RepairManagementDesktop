package pl.polsl.repairmanagementdesktop;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;

import java.net.URI;
import java.util.stream.Stream;

@Component
public class CurrentUser {

    private Integer id;
    private String username;
    private String password;
    private AuthenticationManager.AuthorizedRole role;

    public void setData(Integer id, String username, String password, AuthenticationManager.AuthorizedRole role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public AuthenticationManager.AuthorizedRole getRole() {
        return role;
    }
}
