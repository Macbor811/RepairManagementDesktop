package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;
import java.util.stream.Collectors;

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
	@FXML
	private TextField sequenceNumberTextField;

	@FXML
	private ListView activityTypeListView;

	private final LoaderFactory fxmlLoaderFactory;

	private final RequestService requestService;
	private final ActivityService activityService;
	private final EmployeeService employeeService;
	private final ActivityTypeService activityTypeService;

	private SelectEmployeeScreenController selectEmployeeScreenController;
	private SelectRequestScreenController selectRequestScreenController;
	private Scene selectEmployeeScene;
	private Scene selectRequestScene;
	private EmployeeTableRow employeeTableRow;
	private RequestTableRow requestTableRow;

	@Autowired
	public AddActivityScreenController(LoaderFactory fxmlLoaderFactory, RequestService requestService,ActivityTypeService activityTypeService,ActivityService activityService, EmployeeService employeeService) {
		this.fxmlLoaderFactory = fxmlLoaderFactory;
		this.requestService = requestService;
		this.employeeService = employeeService;
		this.activityTypeService=activityTypeService;
		this.activityService = activityService;

		//prepare customer selection screen
		try {
			FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectEmployeeScreen.fxml");
			Parent selectEmployeeScreen = loader.load();

			selectEmployeeScreenController = loader.getController();

			selectEmployeeScene = new Scene(selectEmployeeScreen);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//prepare request selection screen
		try {
			FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectRequestScreen.fxml");
			Parent selectRequestScreen = loader.load();

			selectRequestScreenController = loader.getController();

			selectRequestScene = new Scene(selectRequestScreen);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initActivityTypeListView(){
		ObservableList<String> activityTypes = FXCollections
				.observableList(
						activityTypeService
								.findAll(0, Integer.MAX_VALUE)
								.getResources()
								.stream()
								.map(entity -> {
									return entity.getType(); //extract ID from URI
								})
								.collect(Collectors.toList())
				);


		activityTypeListView.setItems(activityTypes);
	}

	@FXML
	public void initialize(){
		initActivityTypeListView();
	}


	@FXML
	private void selectRequestButtonClicked(ActionEvent event) throws IOException {

		Scene scene = ((Node) event.getSource()).getScene();
		Stage thisWindow = (Stage) scene.getWindow();

		Stage window = new Stage();


		window.setScene(selectRequestScene);
		window.setResizable(false);
		thisWindow.hide();
		window.showAndWait(); //wait for results from SelectCustomerScreen
		thisWindow.show();

		requestTableRow = selectRequestScreenController.getCurrentSelection();
		if (requestTableRow != null) {
			currentRequestSelectionLabel.setText(requestTableRow.getId());
		}

	}
	@FXML
	private void selectWorkerButtonClicked(ActionEvent event) throws IOException {

		Scene scene = ((Node) event.getSource()).getScene();
		Stage thisWindow = (Stage) scene.getWindow();

		Stage window = new Stage();


		window.setScene(selectEmployeeScene);
		window.setResizable(false);
		thisWindow.hide();
		window.showAndWait(); //wait for results from SelectEmployeeScreen
		thisWindow.show();

		employeeTableRow = selectEmployeeScreenController.getCurrentSelection();
		if (employeeTableRow != null){
			currentWorkerSelectionLabel.setText(employeeTableRow.getFirstName() + " " +employeeTableRow.getLastName());
		}

	}

	@FXML
	private void addActivityButtonClicked(ActionEvent event) {
		EmployeeEntity employee = employeeService.findById(employeeTableRow.getId());
		RequestEntity request = requestService.findById(requestTableRow.getId());
		ActivityTypeEntity type = activityTypeService.findByType((String) activityTypeListView.getSelectionModel().getSelectedItem());

		ActivityEntity activity = new ActivityEntity(Integer.parseInt(sequenceNumberTextField.getText()),descriptionTextField.getText(),"","Open"
				,request.getRegisterDate(),request.getEndDate(),type,request,employee);

		activityService.save(activity);


		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();

	}

	@FXML
	private void cancelButtonClicked(ActionEvent event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}
}