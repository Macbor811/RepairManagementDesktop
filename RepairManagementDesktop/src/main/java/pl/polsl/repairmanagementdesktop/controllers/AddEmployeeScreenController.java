package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.address.AddressService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemService;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeService;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import uk.co.blackpepper.bowman.ClientFactory;

import java.io.IOException;

@Scope("prototype")
@Controller
public class AddEmployeeScreenController {

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

    private final LoaderFactory fxmlLoaderFactory;
    private final EmployeeService employeeService;
    private final AddressService addressService;

    @Autowired
    public AddEmployeeScreenController(LoaderFactory fxmlLoaderFactory, EmployeeService employeeService, AddressService addressService) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @FXML
    public void initialize() {
        roleChoiceBox.setItems(FXCollections.observableArrayList("ADM", "MAN", "WRK"));
        roleChoiceBox.getSelectionModel().select("WRK");
    }

    @FXML
    public void addButtonClicked(ActionEvent event) {
        AddressEntity addressEntity = new AddressEntity(
                postCodeTextField.getText(),
                cityTextField.getText(),
                streetTextField.getText(),
                numberTextField.getText()
                );

        addressService.save(addressEntity);

        EmployeeEntity employeeEntity = new EmployeeEntity(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                phoneNumTextField.getText(),
                roleChoiceBox.getSelectionModel().getSelectedItem().toString(),
                usernameTextField.getText(),
                passwordField.getText(),
                addressEntity
        );

        employeeService.save(employeeEntity);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    public void cancelButtonClidked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}

