package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressRestClient;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

@Controller
public class AddCustomerScreenController {

    @FXML
    private  TextField firstNameTextField;
    @FXML
    private  TextField lastNameTextField;
    @FXML
    private TextField phoneNumTextField;
    @FXML
    private  TextField postCodeTextField;
    @FXML
    private  TextField cityTextField;
    @FXML
    private  TextField streetTextField;
    @FXML
    private  TextField numberTextField;
    @FXML
    private  Button addCustomerButton;
    @FXML
    private Label messageLabel;

    private List<TextField> fieldsList;

    private final CustomerRestClient customerRC;
    private final AddressRestClient addressRC;

    @Autowired
    public AddCustomerScreenController(CustomerRestClient customerRC, AddressRestClient addressRC) {
        this.customerRC = customerRC;
        this.addressRC = addressRC;
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

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        phoneNumTextField.setTextFormatter(textFormatter);
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

            URI id = addressRC.save(address);

            address = addressRC.find(id);

            CustomerEntity customer = new CustomerEntity(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    phoneNumTextField.getText(),
                    address
            );

            customerRC.save(customer);

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            messageLabel.setText("All fields must be filled.");
        }


    }
    
}
