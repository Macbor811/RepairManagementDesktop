package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;


@Controller
public class AddEmployeeScreenController {

	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField phoneNumTextField;
	@FXML
	private TextField roleTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
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

