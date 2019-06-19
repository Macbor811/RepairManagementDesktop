package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
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
public class EmployeesTabController {

    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    @FXML
    private TextField rowsPerPageTextField;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private DatePicker deactivationDateDatePicker;


    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    private TableView<EmployeeTableRow> employeesTableView;

    private final EmployeeService employeeService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public EmployeesTabController(EmployeeService employeeService, LoaderFactory loaderFactory) {
        this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        employeesTableView.getColumns().clear();

        TableColumn<EmployeeTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<EmployeeTableRow, String> firstNameColumn = TableColumnFactory.createColumn("First name", "firstName");
        TableColumn<EmployeeTableRow, String> lastNameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        TableColumn<EmployeeTableRow, String> usernameColumn = TableColumnFactory.createColumn("Username", "username");
        TableColumn<EmployeeTableRow, String> roleColumn = TableColumnFactory.createColumn("Role", "role");
        TableColumn<EmployeeTableRow, String> phoneNumberColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
        TableColumn<EmployeeTableRow, LocalDateTime> deactivationDateColumn = TableColumnFactory.createColumn("Deactivation Date", "deactivationDate");

        employeesTableView.getColumns().addAll(
                idColumn,
                firstNameColumn,
                lastNameColumn,
                roleColumn,
                usernameColumn,
                phoneNumberColumn,
                deactivationDateColumn
        );

        for (var column : employeesTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(firstNameTextField, "firstName"),
                        new TextFieldParamBinding(lastNameTextField, "lastName"),
                        new TextFieldParamBinding(usernameTextField, "username"),
                        new TextFieldParamBinding(roleTextField, "role"),
                        new TextFieldParamBinding(phoneNumberTextField, "phoneNumber"),
                        new DatePickerParamBinding(deactivationDateDatePicker, "deactivationDate")
                )
        );
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
            employeesTableView.getItems().clear();


            for (EmployeeEntity employee: page.getResources()) {
                employeesTableView.getItems().add(new EmployeeTableRow(employee));
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
        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        uriSearchQuery.update();
        updateTable();
    }

    @FXML
    private void updateUserButtonClicked() {

    }
}
