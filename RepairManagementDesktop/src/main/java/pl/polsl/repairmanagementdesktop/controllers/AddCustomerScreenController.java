package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Scope("prototype")
@Controller
public class AddCustomerScreenController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumTextField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Label messageLabel;

    private List<TextField> fieldsList;

    private final CustomerService customerService;
    private final AddressService addressService;

    @Autowired
    public AddCustomerScreenController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @FXML
    public void initialize() {
        fieldsList = Arrays.asList(
                firstNameTextField,
                lastNameTextField,
                phoneNumTextField,
                postCodeTextField,
                cityTextField,
                streetTextField,
                numberTextField
        );

    }

    @FXML
    void addCustomerButtonClicked(ActionEvent event){

        if (fieldsList.stream().noneMatch(field -> field.getText().isEmpty())){

            AddressEntity address =  new AddressEntity(
                    postCodeTextField.getText(),
                    cityTextField.getText(),
                    streetTextField.getText(),
                    numberTextField.getText()
            );

            addressService.save(address);

            CustomerEntity customer = new CustomerEntity(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    phoneNumTextField.getText(),
                    address
            );


                customerService.save(customer);

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();

                stage.close();


        } else {
            messageLabel.setText("All fields must be filled.");
        }


    }
    
}
