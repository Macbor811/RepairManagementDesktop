package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
<<<<<<< HEAD
import org.springframework.context.event.EventListener;
=======
import org.springframework.context.annotation.Scope;
>>>>>>> master
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CustomerSelectedEvent;

@Scope("prototype")
@Controller
public class AddActivityScreenController {

	@FXML
	private Button selectWorkerButton;
	@FXML
	private Button selectRequestButton;
	@FXML
	private Button addActivityButton;
	@FXML
	private Button cancelActivityButton;
	@FXML
	private Label currentWorkerSelectionLabel;
	@FXML
	private Label currentRequestSelectionLabel;
	@FXML
	private Label messageLabel;
	@FXML
	private TextField descriptionTextField;


}

