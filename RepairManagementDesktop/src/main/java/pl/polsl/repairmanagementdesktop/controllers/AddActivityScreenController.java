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
public class AddActivityScreenController {

	@FXML
	private TextField workerTextField;
	@FXML
	private TextField requestTextField;
	@FXML
	private TextField descriptionTextField;
	@FXML
	private Button addActivityButton;
	@FXML
	private Button cancelActivityButton;
	@FXML
	private Label messageLabel;
	
}

