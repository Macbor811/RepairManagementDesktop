package pl.polsl.repairmanagementdesktop.model.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;

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
        return client.get()
     }


     public Iterable<CustomerEntity> findAll(){
         return client.getAll();
     }



}
