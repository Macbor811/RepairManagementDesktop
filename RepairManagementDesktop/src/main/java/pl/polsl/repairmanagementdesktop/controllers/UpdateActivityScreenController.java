package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityStatus;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityUpdateDto;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;
import java.util.stream.Collectors;

@Scope("prototype")
@Controller
public class UpdateActivityScreenController {


    @FXML
    private Button selectWorkerButton;
    @FXML
    private Label currentWorkerSelectionLabel;
    @FXML
    private ListView<String> activityTypeListView;
    @FXML
    private TextArea descriptionTextArea;


    private final ActivityTypeService activityTypeService;

    private ActivityTableRow data;

    private final ActivityService service;

    @FXML
    private ChoiceBox<ActivityStatus> statusChoiceBox;
    @FXML
    private TextArea resultTextArea;

    private SelectEmployeeScreenController selectEmployeeScreenController;
    private Scene selectEmployeeScene;
    private EmployeeTableRow employeeTableRow;

    @Autowired
    public UpdateActivityScreenController(LoaderFactory fxmlLoaderFactory, ActivityTypeService activityTypeService, ActivityService service) {
        this.activityTypeService = activityTypeService;
        this.service = service;

        try {
            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectEmployeeScreen.fxml");
            Parent selectEmployeeScreen = loader.load();

            selectEmployeeScreenController = loader.getController();

            selectEmployeeScene = new Scene(selectEmployeeScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

    }

    private void initActivityTypeListView(){
        //extract ID from URI
        ObservableList<String> activityTypes = FXCollections
                .observableList(
                        activityTypeService
                                .findAll(0, Integer.MAX_VALUE)
                                .getResources()
                                .stream()
                                .map(ActivityTypeEntity::getType)
                                .collect(Collectors.toList())
                );


        activityTypeListView.setItems(activityTypes);
    }

    private void handleStatusChange(Event event){
        final var status = statusChoiceBox.getSelectionModel().getSelectedItem();
        switch (status){
            case OPEN:{
                setSelectedWorker(null);
                selectWorkerButton.setDisable(true);
                resultTextArea.setDisable(true);
                break;
            }
            case IN_PROGRESS:{
                setSelectedWorker(data.getWorkerEntity() != null ? new EmployeeTableRow(data.getWorkerEntity()) : null);
                selectWorkerButton.setDisable(false);
                resultTextArea.setDisable(true);
                break;
            }
            case FINISHED:
            case CANCELLED: {
                setSelectedWorker(data.getWorkerEntity() != null ? new EmployeeTableRow(data.getWorkerEntity()) : null);
                resultTextArea.setDisable(false);
                selectWorkerButton.setDisable(true);
                break;
            }
        }
        resultTextArea.setText(data.getResult());
        descriptionTextArea.setText(data.getDescription());
    }

    private void setCheckBoxItems(ActivityStatus... items){
        statusChoiceBox.setItems(FXCollections.checkedObservableList(
                FXCollections.observableArrayList(items),
                ActivityStatus.class
        ));
    }

    public void setActivityData(ActivityTableRow data){
        this.data = data;
        statusChoiceBox.setOnAction(this::handleStatusChange);
        setSelectedWorker(data.getWorkerEntity() != null ? new EmployeeTableRow(data.getWorkerEntity()) : null);
        final var status = ActivityStatus.fromString(data.getStatus());
        switch (status){
            case OPEN:{
                setCheckBoxItems(
                        ActivityStatus.OPEN,
                        ActivityStatus.IN_PROGRESS,
                        ActivityStatus.FINISHED,
                        ActivityStatus.CANCELLED
                );
                resultTextArea.setDisable(true);
                selectWorkerButton.setDisable(true);
                break;
            }
            case IN_PROGRESS:{
                setCheckBoxItems(
                        ActivityStatus.IN_PROGRESS,
                        ActivityStatus.FINISHED,
                        ActivityStatus.CANCELLED
                );
                resultTextArea.setDisable(true);
                break;
            }
            case FINISHED:
            case CANCELLED: {
                descriptionTextArea.setDisable(true);
                setCheckBoxItems(status);
                selectWorkerButton.setDisable(true);
                break;
            }
        }
        statusChoiceBox.getSelectionModel().select(status);
        initActivityTypeListView();
        var items = activityTypeListView.getItems();
        for (int i=0; i < items.size(); i++){
            if (items.get(i).equals(data.getType())){
                activityTypeListView.getSelectionModel().select(i);
                break;
            }
        }

        resultTextArea.setText(data.getResult());
        descriptionTextArea.setText(data.getDescription());

    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    private String workerId = "";

    @FXML
    private void finalizeButtonClicked(ActionEvent event) {
        var status = statusChoiceBox.getSelectionModel().getSelectedItem();
        if (status == ActivityStatus.IN_PROGRESS && employeeTableRow == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Activity with PRO status must have assigned worker.");
            return;
        }
        var dto = new ActivityUpdateDto(
                data.getSequenceNum(),
                descriptionTextArea.getText(),
                resultTextArea.getText(),
                statusChoiceBox.getSelectionModel().getSelectedItem().toString(),
                workerId,
                activityTypeListView.getSelectionModel().getSelectedItem()
        );


        service.update(data.getId(), dto);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    private void setSelectedWorker(EmployeeTableRow worker){
        employeeTableRow = worker;
        workerId = worker!=null ? worker.getId() : null;
        if (worker != null){
            currentWorkerSelectionLabel.setText(employeeTableRow.getFirstName() + " " +employeeTableRow.getLastName());
        } else {
            currentWorkerSelectionLabel.setText("");
        }
    }

    public void selectWorkerButtonClicked(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        Stage thisWindow = (Stage) scene.getWindow();
        Stage window = new Stage();
        window.setScene(selectEmployeeScene);
        window.setResizable(false);
        thisWindow.hide();
        window.showAndWait(); //wait for results from SelectEmployeeScreen
        thisWindow.show();

       setSelectedWorker(selectEmployeeScreenController.getCurrentSelection());


    }
}
