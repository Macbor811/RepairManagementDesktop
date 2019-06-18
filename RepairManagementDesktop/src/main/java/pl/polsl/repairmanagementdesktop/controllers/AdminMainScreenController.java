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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
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
public class AdminMainScreenController {

    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private DatePicker dateDatePicker;


     private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    private TableView<EmployeeTableRow> UsersTableView;

    private final EmployeeService employeeService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public AdminMainScreenController(EmployeeService employeeService, LoaderFactory loaderFactory) {
         this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        UsersTableView.getColumns().clear();

        TableColumn<EmployeeTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<EmployeeTableRow, String> firstNameColumn = TableColumnFactory.createColumn("firstName", "firstName");
        TableColumn<EmployeeTableRow, String> lastNameColumn = TableColumnFactory.createColumn("lastName", "lastName");
        TableColumn<EmployeeTableRow, String> usernameColumn = TableColumnFactory.createColumn("username", "username");
        TableColumn<EmployeeTableRow, String> phoneNumberColumn = TableColumnFactory.createColumn("phoneNumber", "phoneNumber");
        TableColumn<EmployeeTableRow, LocalDateTime> deactivationDateColumn = TableColumnFactory.createColumn("Deactivation Date", "deactivationDate");

        UsersTableView.getColumns().addAll(
                idColumn,
                firstNameColumn,
                usernameColumn,
                phoneNumberColumn,
                deactivationDateColumn
        );

        for (var column : UsersTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(firstNameTextField, "firstName"),
                        new TextFieldParamBinding(lastNameTextField, "lastName"),
                        new TextFieldParamBinding(usernameTextField, "username"),
                        new TextFieldParamBinding(phoneNumberTextField, "phoneNumber"),
                        new DatePickerParamBinding(dateDatePicker, "deactivationDate")
                )
        );
    }


    private void initPagination() {
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);
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

    private void updateTable() {
        try{
            Page<EmployeeEntity> page = employeeService.findAllMatching(uriSearchQuery.getQueryString(), pagination.getCurrentPageIndex(), rowsPerPage);
            pagination.setPageCount((int) page.getTotalPages());
            UsersTableView.getItems().clear();


            for (EmployeeEntity employee: page.getResources()) {
                UsersTableView.getItems().add(new EmployeeTableRow(employee));
            }
        } catch (ResourceAccessException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.show();
        }

    }

    @FXML
    private void addUserButtonClicked(ActionEvent event) throws IOException {

    }

    @FXML
    private void showUsersButtonClicked() {
        uriSearchQuery.update();
        updateTable();
    }
    @FXML
    private void updateUserButtonClicked() {

    }

}
