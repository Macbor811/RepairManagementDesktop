package pl.polsl.repairmanagementdesktop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import pl.polsl.repairmanagementdesktop.utils.auth.CurrentUser;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;

@org.springframework.context.annotation.Configuration
public class ConfiguredClientFactory {

    @Autowired
    private CurrentUser currentUser;

    private String username;
    private String password;

    ClientHttpRequestInterceptor interceptor;

    public void setCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public static ClientHttpRequestInterceptor basicAuthInterceptor(String username, String password){
        return (request, body, execution) -> {
            request.getHeaders().setBasicAuth(username, password);
            return execution.execute(request, body);
        };
    }


    @Bean
    @Primary
    public ClientFactory configuredClient(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> {
                    restTemplate.setErrorHandler(handler);

                    restTemplate.getInterceptors().add((request, body, execution) -> {
                        request.getHeaders().add("Authorization", currentUser.getBasicAuth());
                        return execution.execute(request, body);
                    });

                })
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }

    @Bean("noHandler")
    public ClientFactory configuredClientNoHandler(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> {
                    restTemplate.getInterceptors().add((request, body, execution) -> {
                        request.getHeaders().add("Authorization", currentUser.getBasicAuth());
                        return execution.execute(request, body);
                    });

                })
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }

}
