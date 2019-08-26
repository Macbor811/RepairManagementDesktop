package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;

import java.util.Arrays;
import java.util.List;

@Scope("prototype")
@Controller
public class UpdateEmployeeScreenController {

    public DatePicker deactivationDatePicker;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumTextField;
    @FXML
    private ChoiceBox roleChoiceBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button cancelEmployeeButton;
    @FXML
    private Label messageLabel;

    private List<TextField> fieldsList;

    private final EmployeeService employeeService;
    private final AddressService addressService;

    @Autowired
    public UpdateEmployeeScreenController(EmployeeService employeeService, AddressService addressService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @FXML
    public void initialize() {
        roleChoiceBox.setItems(FXCollections.observableArrayList("ADM", "MAN", "WRK"));
        roleChoiceBox.getSelectionModel().select("WRK");
        fieldsList = Arrays.asList(
                firstNameTextField,
                lastNameTextField,
                phoneNumTextField,
                usernameTextField,
                passwordField,
                postCodeTextField,
                cityTextField,
                streetTextField,
                numberTextField
        );

    }

    @FXML
    void updateEmployeeButtonClicked(ActionEvent event) {

        if (fieldsList.stream().noneMatch(field -> field.getText().isEmpty())) {

            AddressEntity address = new AddressEntity(
                    postCodeTextField.getText(),
                    cityTextField.getText(),
                    streetTextField.getText(),
                    numberTextField.getText()
            );

            //addressService.save(address);

            EmployeeEntity employee = new EmployeeEntity(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    phoneNumTextField.getText(),
                    roleChoiceBox.getValue().toString(),
                    usernameTextField.getText(),
                    passwordField.getText(),
                    address
            );


            // employeeService.save(employee);

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();

            stage.close();


        } else {
            messageLabel.setText("All fields must be filled.");
        }
    }
    public void setEmployee(EmployeeEntity employee)
    {
        firstNameTextField.setText(employee.getFirstName());
        lastNameTextField.setText(employee.getLastName());
        phoneNumTextField.setText(employee.getPhoneNumber());
        postCodeTextField.setText(employee.getAddress().getPostCode());
        usernameTextField.setText(employee.getUsername());
        cityTextField.setText(employee.getAddress().getCity());
        streetTextField.setText(employee.getAddress().getStreet());
        numberTextField.setText(employee.getAddress().getNumber());
    }


}

