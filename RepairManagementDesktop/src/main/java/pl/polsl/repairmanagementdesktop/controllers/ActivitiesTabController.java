package pl.polsl.repairmanagementdesktop.controllers;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
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
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
import pl.polsl.repairmanagementdesktop.utils.search.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringJoiner;


@Scope("prototype")
@Controller
public class ActivitiesTabController extends TabController<ActivityEntity, ActivityTableRow>{

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


    private final ActivityService activityService;

    private final EmployeeService employeeService;

    private final LoaderFactory loaderFactory;

    @Autowired
    public ActivitiesTabController(ActivityService activityService, EmployeeService employeeService, LoaderFactory loaderFactory) {
        super(activityService, ActivityTableRow::new);
        this.activityService = activityService;
        this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }


    @Override
    protected void initTableView() {
        tableView.getColumns().clear();


        TableColumn<ActivityTableRow, Integer> sequenceNumColumn = TableColumnFactory.createColumn("Seq. no", "sequenceNum");
        TableColumn<ActivityTableRow, Integer> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ActivityTableRow, String> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        registeredDateColumn.setPrefWidth(115);
        TableColumn<ActivityTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<ActivityTableRow, String> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        finalizedDateColumn.setPrefWidth(115);
        TableColumn<ActivityTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        descriptionColumn.setPrefWidth(130);
        TableColumn<ActivityTableRow, String> resultColumn = TableColumnFactory.createColumn("Result", "result");
        resultColumn.setPrefWidth(130);
        TableColumn<ActivityTableRow, String> workerColumn = TableColumnFactory.createColumn("Worker", "worker");
        workerColumn.setPrefWidth(130);

        tableView.getColumns().addAll(
                sequenceNumColumn,
                idColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                descriptionColumn,
                resultColumn,
                workerColumn

        );

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

    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

        statusMenuButton.getItems().addAll(
                new CheckMenuItem("OPN"),
                new CheckMenuItem("PRO"),
                new CheckMenuItem("FIN"),
                new CheckMenuItem("CAN")
        );
        statusMenuButton.setOnHidden(e -> onStatusesUpdate());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registerDate"),
                        new DatePickerParamBinding(finalizedDatePicker, "endDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(resultTextField, "result"),
                        new ConstantParamBinding("sort", "sequenceNum,asc"),
                        new SupplierBasedParamBinding("worker.id", () -> workerId),
                        new CheckMenuParamBinding(statusMenuButton, "status")
                )
        );

        var customerAutoCompletion = new AutoCompletionTextFieldBinding<>(workerTextField, t -> employeeService.findByFullName(t.getUserText(), "wrk"));
        customerAutoCompletion.setOnAutoCompleted(t -> {
            workerId = t.getCompletion().substring(t.getCompletion().lastIndexOf("; ") + 1);
            workerTextField.setEditable(false);
        });

    }




    public void addActivity(ActionEvent event) throws IOException  {
        FXMLLoader loader = loaderFactory.load("/fxml/addActivityScreen.fxml");

        Parent addActivityScreen = loader.load();

        Scene nextScene = new Scene(addActivityScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    public void finalizeActivity(ActionEvent event){
        ActivityTableRow selection = tableView.getSelectionModel().getSelectedItem();

        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/finalizeActivityScreen.fxml");

                Parent detailsScreen = loader.load();
                FinalizeActivityScreenController dsc = loader.getController();

                dsc.setActivityTableRow(selection);

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

    public void updateActivity(ActionEvent event) {
        try{
            FXMLLoader loader = loaderFactory.load("/fxml/updateActivityScreen.fxml");

            Parent updateActivityScreen = loader.load();
            Scene nextScene = new Scene(updateActivityScreen);
            UpdateActivityScreenController addActivityScreenController = loader.getController();
            addActivityScreenController.setActivity(activityService.findById(getCurrentSelection().getId().toString()));
            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){}

    }

    public void showActivityDetails(ActionEvent event) {

        try
        {
            FXMLLoader loader = loaderFactory.load("/fxml/detailsScreen.fxml");

            Parent detailsScreen = loader.load();
            DetailsScreenController dsc = loader.getController();
            ActivityEntity ae = activityService.findById(getCurrentSelection().getId().toString());
            dsc.setText(ae.getDescription(),ae.getResult());
            Scene nextScene = new Scene(detailsScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){

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
}
