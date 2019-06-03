package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

@Component
public class AuthenticationManager {

    //private final OAuth2RestTemplate restTemplate;

    @Value("${server.address}")
    private String server;

    private final HttpComponentsClientHttpRequestFactory sslRequestFactory;

    @Autowired
    public AuthenticationManager(HttpComponentsClientHttpRequestFactory sslRequestFactory) {
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
       ADMIN, MANAGER, WORKER, FAILED
    }

    public AuthorizedRole authorizeForRole(String username, String password){

        var restTemplate = oAuth2RestTemplate(username, password);
        restTemplate.setRequestFactory(sslRequestFactory);



        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        provider.setRequestFactory(sslRequestFactory);
        restTemplate.setAccessTokenProvider(provider);
        try{
            var token = restTemplate.getAccessToken();

            return AuthorizedRole.ADMIN;

        } catch (OAuth2AccessDeniedException e) {
            return AuthorizedRole.FAILED;
        }


    }
}
