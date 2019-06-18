package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.CustomerSelectedEvent;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityEntity;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.DatePickerParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;


@Scope("prototype")
@Controller
public class ActivitiesTabController {



    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
   // private TextField rowsPerPageTextField;
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
    private final LoaderFactory loaderFactory;

    @Autowired
    public ActivitiesTabController(ActivityService activityService, LoaderFactory loaderFactory) {
        this.activityService = activityService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        activityTableView.getColumns().clear();

        TableColumn<ActivityTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ActivityTableRow, LocalDateTime> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        TableColumn<ActivityTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<ActivityTableRow, LocalDateTime> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        TableColumn<ActivityTableRow, Integer> sequenceNumDateColumn = TableColumnFactory.createColumn("Finalized Date", "sequenceNum");
        TableColumn<ActivityTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        TableColumn<ActivityTableRow, String> requestColumn = TableColumnFactory.createColumn("Result", "result");

        activityTableView.getColumns().addAll(
                idColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                sequenceNumDateColumn,
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
                        new DatePickerParamBinding(registeredDatePicker, "registeredDate"),
                        new TextFieldParamBinding(statusTextField, "statusText"),
                        new DatePickerParamBinding(finalizedDatePicker, "finalizedDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(clientTextField, "client"),
                        new TextFieldParamBinding(itemTextField, "item"),
                        new TextFieldParamBinding(requestTextField, "request")
                )
        );
    }

    private void initPagination() {
       // rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);

//        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
    }

    @FXML
    public void initialize() {
        initActivityTableView();
        initQueryFields();
        initPagination();

    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }


    @FXML
    private void addActivityButtonClicked(ActionEvent event) throws IOException {
        Parent managerMainScreen = loaderFactory.load("/fxml/addCustomerScreen.fxml").load();
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    /**
     * Updates search settings from text fields to show new results.
     */
    @FXML
    private void showActivityButtonClicked() {
        uriSearchQuery.update();
        updateTable();
    }
    @FXML
    private void updatesActivityButtonClicked() {

    }

    private void updateTable() {
        try{
            Page<ActivityEntity> page = activityService.findAllMatching(uriSearchQuery.getQueryString(), pagination.getCurrentPageIndex(), rowsPerPage);
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


}
