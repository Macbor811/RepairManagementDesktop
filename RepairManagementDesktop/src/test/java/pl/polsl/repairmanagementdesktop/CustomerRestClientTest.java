package pl.polsl.repairmanagementdesktop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRestClientTest {

    @Autowired
    CustomerService restClient;


    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void save(){
      //  AddressDTO address = new AddressDTO("42-600", "Miasto", "Ulica", 42);
      //  CustomerDTO sentDTO = new CustomerDTO("Jan", "Kowalski", "testNumber", address);

      //  ResponseEntity<CustomerDTO> response = restClient.save(sentDTO);
      //  String locationUri =  response.getHeaders().get("Location").get(0);
      //  CustomerDTO receivedDTO = restTemplate.getForObject("http://localhost:8080/"  + "customer" + locationUri, CustomerDTO.class);

      //  Assert.assertEquals(sentDTO, receivedDTO);

    }

    @Test
    public void contextLoads() {

       // Assert.assertEquals(1, clientRepository.findByName("nazwa").size());
    }

}
