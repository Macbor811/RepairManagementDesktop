package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;

@org.springframework.context.annotation.Configuration
public class ConfiguredClientFactory {

    @Bean
    public ClientFactory configuredClient(@Value("${server.address}") String server) {
        ClientFactory factory = Configuration.builder().setBaseUri(server + "/api").build().buildClientFactory();
        return factory;
    }


}
