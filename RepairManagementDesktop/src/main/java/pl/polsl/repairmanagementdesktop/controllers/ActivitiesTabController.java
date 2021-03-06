package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.*;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.util.Arrays;


@Scope("prototype")
@Controller
public class ActivitiesTabController {



    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;

    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;

    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker registeredDatePicker;
    @FXML
    private TextField statusTextField;
    @FXML
    private DatePicker finalizedDatePicker;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField clientTextField;
    @FXML
    private TextField itemTextField;
    @FXML
    private TextField requestTextField;

    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    private TableView<ActivityTableRow> activityTableView;

    private final ActivityService activityService;

    private final EmployeeService employeeService;

    private final LoaderFactory loaderFactory;

    @Autowired
    public ActivitiesTabController(ActivityService activityService, EmployeeService employeeService, LoaderFactory loaderFactory) {
        this.activityService = activityService;
        this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {

        activityTableView.getColumns().clear();

        TableColumn<ActivityTableRow, Integer> sequenceNumDateColumn = TableColumnFactory.createColumn("Seq. no", "sequenceNum");
        TableColumn<ActivityTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ActivityTableRow, String> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        TableColumn<ActivityTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<ActivityTableRow, String> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        TableColumn<ActivityTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        TableColumn<ActivityTableRow, String> requestColumn = TableColumnFactory.createColumn("Result", "result");

        activityTableView.getColumns().addAll(
                sequenceNumDateColumn,
                idColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                descriptionColumn,
                requestColumn

        );

        for (var column : activityTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }


    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registerDate"),
                        new TextFieldParamBinding(statusTextField, "status"),
                        new DatePickerParamBinding(finalizedDatePicker, "endDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(clientTextField, "client"),
                        new TextFieldParamBinding(itemTextField, "item"),
                        new TextFieldParamBinding(requestTextField, "request"),
                        new ConstantParamBinding("sort", "sequenceNum,asc")
                )
        );
    }

    public void addParamBindings(ParamBinding ... bindings){
        for (var binding : bindings){
            uriSearchQuery.getBindings().add(binding);
        }
        uriSearchQuery.update();
    }


    private void initPagination() {
        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);
        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
    }

    @FXML
    public void initialize() {
        initQueryFields();
        initPagination();
        initActivityTableView();
    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
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
        ActivityTableRow selection = activityTableView.getSelectionModel().getSelectedItem();

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
            addActivityScreenController.setActivity(activityService.findById(getCurrentSelection().getId()));
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
            ActivityEntity ae = activityService.findById(getCurrentSelection().getId());
            dsc.setText(ae.getDescription(),ae.getResult());
            Scene nextScene = new Scene(detailsScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }
        catch (IOException e)
        {}
    }


    /**
     * Updates search settings from text fields to show new results.
     */
    @FXML
    private void showActivityButtonClicked() {

        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        uriSearchQuery.update();
        updateTable();
    }



    private void updateTable() {
            try {
                Page<ActivityEntity> page = activityService.findAllMatching(uriSearchQuery, pagination.getCurrentPageIndex(), rowsPerPage);
                pagination.setPageCount((int) page.getTotalPages());
                activityTableView.getItems().clear();


                for (ActivityEntity activity: page.getResources()) {
                    activityTableView.getItems().add(new ActivityTableRow(activity));
                }
            } catch (ResourceAccessException e){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Connection error");
                errorAlert.setContentText(e.getMessage());
                errorAlert.show();
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


    ActivityTableRow getCurrentSelection(){
        return activityTableView.getSelectionModel().getSelectedItem();
    }
}
