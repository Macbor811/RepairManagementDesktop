package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.utils.ConfiguredClientFactory;

import java.util.stream.Stream;

@Component
public class AuthenticationManager {

    //private final OAuth2RestTemplate restTemplate;


    private final CurrentUser user;
    //private final EmployeeService employeeService;

    private final ConfiguredClientFactory configuredClientFactory;

    @Value("${server.address}")
    private String server;

    private final HttpComponentsClientHttpRequestFactory sslRequestFactory;

    @Autowired
    public AuthenticationManager(CurrentUser user,  ConfiguredClientFactory configuredClientFactory, HttpComponentsClientHttpRequestFactory sslRequestFactory) {
        this.user = user;
        this.configuredClientFactory = configuredClientFactory;
        this.sslRequestFactory = sslRequestFactory;
    }


    private OAuth2RestTemplate oAuth2RestTemplate(String username, String password){
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        //List scopes = new ArrayList<String>(2);
        //scopes.add("write");
       // scopes.add("read");
        resource.setAccessTokenUri(server + "/oauth/token");
        resource.setClientId("client-id");
        resource.setClientSecret("client-secret");
        resource.setGrantType("password");
        //resource.setScope(scopes);

        resource.setUsername(username);
        resource.setPassword(password);
        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
    }


    public enum AuthorizedRole{
        ADMIN("ROLE_ADM"),
        MANAGER("ROLE_MAN"),
        WORKER("ROLE_WRK"),
        FAILED("ROLE_FAILED");

        private final String text;

        AuthorizedRole(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }


    public AuthorizedRole authenticate(String username, String password){

        var restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(ConfiguredClientFactory.basicAuthInterceptor(username, password));

        //restTemplate.setRequestFactory(sslRequestFactory);

        UserData data = restTemplate.getForObject(server + "user/me", UserData.class);
        if (data.getActive()){


            var role  = Stream.of(AuthenticationManager.AuthorizedRole.values())
                    .filter(r -> r.toString().equals("ROLE_" + data.getRole()))
                    .findFirst().orElse(AuthenticationManager.AuthorizedRole.FAILED);

            user.setData(Integer.parseInt(data.getId()), username, password, role);

            return user.getRole();
        } else {
            return AuthorizedRole.FAILED;
        }



    }
}
