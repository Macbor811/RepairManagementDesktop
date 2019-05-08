package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.NumericField;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;

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

    public AddCustomerScreenController(CustomerRestClient customerRC) {
        this.customerRC = customerRC;
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

//            CustomerDTO customer = new CustomerDTO(
//                    firstNameTextField.getText(),
//                    lastNameTextField.getText(),
//                    phoneNumTextField.getText(),
//                    new AddressDTO(
//                            postCodeTextField.getText(),
//                            cityTextField.getText(),
//                            streetTextField.getText(),
//                            numberTextField.getText()
//                    )
//            );
//
//            customerRC.save(customer);

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            messageLabel.setText("All fields must be filled.");
        }


    }
    
}
