package pl.polsl.repairmanagementdesktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.RestClient;
import pl.polsl.repairmanagementdesktop.model.AddressDTO;

import java.util.List;

@Controller
public class MainController {

    @FXML
    private Label label;

    @FXML
    private TableView table;


    RestClient client = new RestClient();

    @FXML
    private void buttonClicked(){
//        RestTemplate restTemplate = new RestTemplate();
//      //  AddressDTO address = restTemplate.getForObject("http://localhost:8080/address/save", AddressDTO.class);
//
//        ResponseEntity<List<AddressDTO>> rateResponse =
//                restTemplate.exchange("http://localhost:8080/address",
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AddressDTO>>() {
//                        });
        List<AddressDTO> addresses = client.toResponseList("/address", null, AddressDTO[].class);
        label.setText(addresses.get(0).toString());
    }


}
