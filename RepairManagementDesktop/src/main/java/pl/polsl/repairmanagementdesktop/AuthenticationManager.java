package pl.polsl.repairmanagementdesktop;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.utils.ConfiguredClientFactory;

import java.util.stream.Stream;

@Component
public class AuthenticationManager {



    public class LoginException extends Exception{

        public LoginException(String message) {super(message);};
    }


    private final CurrentUser user;

    private final ConfiguredClientFactory configuredClientFactory;

    @Value("${server.address}")
    private String server;

    private final HttpComponentsClientHttpRequestFactory sslRequestFactory;

    @Autowired
    public AuthenticationManager(CurrentUser user, ConfiguredClientFactory configuredClientFactory, HttpComponentsClientHttpRequestFactory sslRequestFactory) {
        this.user = user;
        this.configuredClientFactory = configuredClientFactory;
        this.sslRequestFactory = sslRequestFactory;
    }



    public enum AuthorizedRole {
        ADMIN("ROLE_ADM"),
        MANAGER("ROLE_MAN"),
        WORKER("ROLE_WRK");

        private final String text;

        AuthorizedRole(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

        public AuthorizedRole authenticate(String username, String password) throws LoginException {

            var restTemplate = new RestTemplate();

            try {
                restTemplate.getInterceptors().add(ConfiguredClientFactory.basicAuthInterceptor(username, password));

            UserData data = restTemplate.getForObject(server + "user/me", UserData.class);

            if (data.getActive()) {


                var role = Stream.of(AuthenticationManager.AuthorizedRole.values())
                        .filter(r -> r.toString().equals("ROLE_" + data.getRole()))
                        .findFirst().orElseThrow(() ->  new LoginException("Invalid role."));

                user.setData(Integer.parseInt(data.getId()), username, password, role);

                return user.getRole();
            } else {
                throw  new LoginException("Account is deactivated.");
            }
            } catch (HttpClientErrorException e){
                throw new LoginException("Wrong username or password.");
            }


        }
}

