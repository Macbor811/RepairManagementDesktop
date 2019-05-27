package pl.polsl.repairmanagementdesktop.model.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;

import java.net.URI;

@Component
public class AddressService {

    private final Client<AddressEntity> client;

    @Autowired
    public AddressService(ClientFactory factory){

        client = factory.create(AddressEntity.class);

    }

    public AddressEntity find(URI uri){
        return client.get(uri);
    }

    public URI save(AddressEntity customer){
        return client.post(customer);
    }


    public Iterable<AddressEntity> findAll(){
        return client.getAll();
    }
}
