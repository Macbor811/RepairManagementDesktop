package pl.polsl.repairmanagementdesktop.model.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Component
public class ClientRestClient {

    private final static String BASE_URI = "/client";
    private final RestTemplate restTemplate;

    @Autowired
    ClientRestClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


     public void save(ClientDTO client){
         HttpEntity<ClientDTO> request = new HttpEntity<>(client);
         restTemplate.postForObject(BASE_URI, request, ClientDTO.class);
     }


     public List<ClientDTO> findAll(){
         return Arrays.asList(restTemplate.getForObject(BASE_URI, ClientDTO[].class));
     }

}
