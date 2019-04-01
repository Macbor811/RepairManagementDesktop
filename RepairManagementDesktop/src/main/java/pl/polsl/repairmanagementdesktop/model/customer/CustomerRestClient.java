package pl.polsl.repairmanagementdesktop.model.customer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class CustomerRestClient {

    private final static String BASE_URI = "customer";
    private final RestTemplate restTemplate;


    public CustomerRestClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


     public void save(CustomerDTO client){
         HttpEntity<CustomerDTO> request = new HttpEntity<>(client);
         restTemplate.postForObject(BASE_URI, request, CustomerDTO.class);
     }


     public List<CustomerDTO> findAll(){
         return Arrays.asList(restTemplate.getForObject(BASE_URI, CustomerDTO[].class));
     }

     public Optional<CustomerDTO> findById(Integer id){
        try {
            return Optional.ofNullable(restTemplate.getForObject(BASE_URI + "/" + id, CustomerDTO.class));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return Optional.empty();
            }
            throw e;
        }
     }



}
