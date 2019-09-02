package pl.polsl.repairmanagementdesktop.controllers;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.abstr.TabController;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityStatus;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeEntity;
import pl.polsl.repairmanagementdesktop.model.activitytype.ActivityTypeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
import pl.polsl.repairmanagementdesktop.utils.search.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Scope("prototype")
@Controller
public class ActivitiesTabController extends TabController<ActivityEntity, ActivityTableRow>{

    @FXML
    private Button clearWorkerButton;
    @FXML
    private MenuButton statusMenuButton;
    @FXML
    private TextField resultTextField;
    @FXML
    private Label selectedStatusesLabel;
    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker registeredDatePicker;
    @FXML
    private DatePicker finalizedDatePicker;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField workerTextField;
    @FXML
    private ComboBox<ActivityTypeEntity> typeComboBox;


    private final ActivityService activityService;
    private final ActivityTypeService activityTypeService;

    private final EmployeeService employeeService;

    private final LoaderFactory loaderFactory;

    @Autowired
    public ActivitiesTabController(ActivityService activityService, ActivityTypeService activityTypeService, EmployeeService employeeService, LoaderFactory loaderFactory) {
        super(activityService, ActivityTableRow::new);
        this.activityService = activityService;
        this.activityTypeService = activityTypeService;
        this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }

    private Boolean workerMode = false;


    private  void commonColumns(){
        TableColumn<ActivityTableRow, Integer> sequenceNumColumn = TableColumnFactory.createColumn("Seq. no", "sequenceNum");
        TableColumn<ActivityTableRow, Integer> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ActivityTableRow, String> typeColumn = TableColumnFactory.createColumn("Type", "type");
        TableColumn<ActivityTableRow, String> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        registeredDateColumn.setPrefWidth(115);
        TableColumn<ActivityTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<ActivityTableRow, String> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        finalizedDateColumn.setPrefWidth(115);
        TableColumn<ActivityTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        descriptionColumn.setPrefWidth(130);
        TableColumn<ActivityTableRow, String> resultColumn = TableColumnFactory.createColumn("Result", "result");
        resultColumn.setPrefWidth(130);
        tableView.getColumns().addAll(
                sequenceNumColumn,
                idColumn,
                typeColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                descriptionColumn,
                resultColumn
        );
    }

    @Override
    protected void initTableView() {
        tableView.getColumns().clear();

        commonColumns();
        if (!workerMode) {
            TableColumn<ActivityTableRow, String> workerColumn = TableColumnFactory.createColumn("Worker", "worker");
            workerColumn.setPrefWidth(130);
            tableView.getColumns().add(workerColumn);
        }

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private String workerId = "";

    @FXML
    private void clearWorkerButtonClicked(ActionEvent actionEvent) {
        workerTextField.clear();
        workerTextField.setEditable(true);
        workerId = "";
    }

    private String typeId = "";


    private void commonBindings(){
        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registerDate"),
                        new DatePickerParamBinding(finalizedDatePicker, "endDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(resultTextField, "result"),
                        new ConstantParamBinding("sort", "sequenceNum,asc"),
                        new CheckMenuParamBinding(statusMenuButton, "status"),
                        new SupplierBasedParamBinding("activityType.id", () -> typeId)
                )
        );
    }

    private final ActivityTypeEntity dummy = new ActivityTypeEntity();


    private void initTypeComboBox(){
        typeComboBox.getItems().clear();
        typeComboBox.getItems().add(dummy);
        typeComboBox.getItems().addAll(FXCollections
                .observableList(
                        activityTypeService
                                .findAll(0, Integer.MAX_VALUE)
                                .getResources()
                ));
    }

    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());
        initTypeComboBox();

        typeComboBox.setOnAction(e -> {
            var selection = typeComboBox.getSelectionModel().getSelectedItem();
            if (selection!= null && selection != dummy) {
                if (selection.getUri() == null){
                    typeComboBox.getSelectionModel().clearSelection();
                    typeId = "";
                }
                var uriString = selection.getUri().toString();
                typeId =  uriString.substring(uriString.lastIndexOf("/") + 1);
            } else {
                typeId =  "";
            }
        });

        statusMenuButton.getItems().addAll(
                Stream.of(ActivityStatus.values()).map(status -> new CheckMenuItem(status.toString())).collect(Collectors.toList())
        );
        statusMenuButton.setOnHidden(e -> onStatusesUpdate());

        commonBindings();
        if (!workerMode){
            uriSearchQuery.getBindings().add(
                    new SupplierBasedParamBinding("worker.id", () -> workerId)
            );
            var workerAutoCompletion = new AutoCompletionTextFieldBinding<>(workerTextField, t -> employeeService.findByFullName(t.getUserText(), "wrk"));
            workerAutoCompletion.setOnAutoCompleted(t -> {
                workerId = t.getCompletion().substring(t.getCompletion().lastIndexOf("; ") + 1);
                workerTextField.setEditable(false);
            });
        } else {
            workerTextField.setVisible(false);
            clearWorkerButton.setVisible(false);
        }

    }


    public void addActivity(String requestId) throws IOException  {
        FXMLLoader loader = loaderFactory.load("/fxml/addActivityScreen.fxml");

        Parent addActivityScreen = loader.load();
        Scene nextScene = new Scene(addActivityScreen);
        AddActivityScreenController addActivityScreenController = loader.getController();
        addActivityScreenController.setRequest(requestId);
        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.showAndWait();
        initTypeComboBox();
    }

    public void updateActivity(ActionEvent event){
        ActivityTableRow selection = tableView.getSelectionModel().getSelectedItem();
        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/updateActivityScreen.fxml");

                Parent detailsScreen = loader.load();
                UpdateActivityScreenController dsc = loader.getController();

                dsc.setActivityData(selection);

                Scene nextScene = new Scene(detailsScreen);

                Stage window = new Stage();

                window.setScene(nextScene);
                window.setResizable(false);
                window.show();
            }
            catch (IOException e)
            {}
        }
    }


    @FXML
    private void clearRegisterDateButtonClicked(ActionEvent event) {
        registeredDatePicker.setValue(null);
    }

    @FXML
    private void clearFinalizedDateButtonClicked(ActionEvent event) {
        finalizedDatePicker.setValue(null);
    }


    public void onStatusesUpdate() {
        var joiner = new StringJoiner( ", ");
        joiner.setEmptyValue("");
        for (var item : statusMenuButton.getItems().filtered(it -> it instanceof CheckMenuItem)){
            var checkItem = (CheckMenuItem) item;
            if (checkItem.isSelected()){
                joiner.add(checkItem.getText());
            }
        }
        selectedStatusesLabel.setText(joiner.toString());
    }

    public TableView<ActivityTableRow> getTableView(){
        return tableView;
    }

    public void moveSelected(Integer increment){
        int index = tableView.getSelectionModel().getSelectedIndex();
        var moved = tableView.getSelectionModel().getSelectedItem();
        var other = tableView.getItems().get(index - increment);
        moved.reorder(-increment);
        other.reorder(increment);
        // swap items
        tableView.getItems().add(index-increment, tableView.getItems().remove(index));
        // select item at new position
        tableView.getSelectionModel().clearAndSelect(index-increment);
        activityService.reorder(moved.getId().toString(), -increment);
        activityService.reorder(other.getId().toString(), increment);
    }


    public void setWorkerMode(Boolean workerMode) {
        this.workerMode = workerMode;
    }
}
