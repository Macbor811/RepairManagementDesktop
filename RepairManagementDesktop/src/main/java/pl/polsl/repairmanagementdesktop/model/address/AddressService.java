package pl.polsl.repairmanagementdesktop.model.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;

import java.net.URI;

@Service
public class AddressService {

    private final Client<AddressEntity> client;

    @Autowired
    public AddressService(ClientFactory factory){

        client = factory.create(AddressEntity.class);

    }

    public URI save(AddressEntity entity){
        return client.post(entity);
    }

}
