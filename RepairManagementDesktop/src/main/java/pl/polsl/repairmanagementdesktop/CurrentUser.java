package pl.polsl.repairmanagementdesktop;

import org.springframework.stereotype.Component;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;

import java.net.URI;
import java.util.stream.Stream;

@Component
public class CurrentUser {

    private URI uri;


    private String username;
    private AuthenticationManager.AuthorizedRole role;


    public void set(EmployeeEntity entity){
        uri = entity.getUri();
        username = entity.getUsername();

        role = Stream.of(AuthenticationManager.AuthorizedRole.values())
                .filter(role -> role.toString().equals(entity.getRole()))
                .findFirst().orElse(AuthenticationManager.AuthorizedRole.FAILED);
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


}
