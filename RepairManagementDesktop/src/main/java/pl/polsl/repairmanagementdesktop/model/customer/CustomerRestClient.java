package pl.polsl.repairmanagementdesktop.model.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class CustomerRestClient {

    private final Client<CustomerEntity> client;

    @Autowired
    public CustomerRestClient(ClientFactory factory){

        client = factory.create(CustomerEntity.class);

    }

     public void save(CustomerEntity customer){
        client.post(customer);
     }

     public CustomerEntity findById(Integer id){
        return client.get();
     }


     public Page<CustomerEntity> findAll(int page, int size){

        return client.getPage(URI.create(client.getBaseUri().toString()), page, size);
     }

    public Page<CustomerEntity> findAll(String params ,int page, int size){
        return client.getPage(URI.create(client.getBaseUri().toString() + params), page, size);
    }
}
