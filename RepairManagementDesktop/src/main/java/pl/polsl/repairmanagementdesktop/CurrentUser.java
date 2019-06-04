package pl.polsl.repairmanagementdesktop;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;

import java.net.URI;
import java.util.stream.Stream;

@Component
public class CurrentUser {

    private URI uri;


    private String username;
    private AuthenticationManager.AuthorizedRole role;

    private OAuth2AccessToken token;


    public void setEmployee(EmployeeEntity entity){
        uri = entity.getUri();
        username = entity.getUsername();

        role = Stream.of(AuthenticationManager.AuthorizedRole.values())
                .filter(role -> role.toString().equals("ROLE_" + entity.getRole()))
                .findFirst().orElse(AuthenticationManager.AuthorizedRole.FAILED);
    }

    public void setToken(OAuth2AccessToken token) {
        this.token = token;
    }

    public URI getUri() {
        return uri;
    }
    public String getUsername() {
        return username;
    }
    public AuthenticationManager.AuthorizedRole getRole() {
        return role;
    }
    public OAuth2AccessToken getToken() {
        return token;
    }
}
