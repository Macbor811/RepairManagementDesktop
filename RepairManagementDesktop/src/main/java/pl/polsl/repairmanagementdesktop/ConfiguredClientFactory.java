package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;

@org.springframework.context.annotation.Configuration
public class ConfiguredClientFactory {
    
    private final ClientFactory factory;


    public ConfiguredClientFactory(@Value("${server.address}") String server) {
        factory = Configuration.builder().setBaseUri(server).build().buildClientFactory();


    }


}
