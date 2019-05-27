package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.RestTemplateConfigurer;

@org.springframework.context.annotation.Configuration
public class ConfiguredClientFactory {

    @Bean
    public ClientFactory configuredClient(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> restTemplate.setErrorHandler(handler))
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }


}
