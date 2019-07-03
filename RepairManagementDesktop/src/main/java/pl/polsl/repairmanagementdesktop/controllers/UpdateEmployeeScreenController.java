package pl.polsl.repairmanagementdesktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public class UpdateEmployeeScreenController {

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

}

