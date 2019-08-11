package pl.polsl.repairmanagementdesktop.controllers;

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
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
import pl.polsl.repairmanagementdesktop.utils.search.DatePickerParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class EmployeesTabController extends TabController<EmployeeEntity, EmployeeTableRow> {

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


    private final EmployeeService employeeService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public EmployeesTabController(EmployeeService employeeService, LoaderFactory loaderFactory) {
        super(employeeService, EmployeeTableRow::new);
        this.employeeService = employeeService;
        this.loaderFactory = loaderFactory;
    }

    @Override
    protected void initTableView() {
        tableView.getColumns().clear();

        TableColumn<EmployeeTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<EmployeeTableRow, String> firstNameColumn = TableColumnFactory.createColumn("First name", "firstName");
        TableColumn<EmployeeTableRow, String> lastNameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        TableColumn<EmployeeTableRow, String> usernameColumn = TableColumnFactory.createColumn("Username", "username");
        TableColumn<EmployeeTableRow, String> roleColumn = TableColumnFactory.createColumn("Role", "role");
        TableColumn<EmployeeTableRow, String> phoneNumberColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
        TableColumn<EmployeeTableRow, LocalDateTime> deactivationDateColumn = TableColumnFactory.createColumn("Deactivation Date", "deactivationDate");

        tableView.getColumns().addAll(
                idColumn,
                firstNameColumn,
                lastNameColumn,
                roleColumn,
                usernameColumn,
                phoneNumberColumn,
                deactivationDateColumn
        );

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());
        rowsPerPageTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

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



    public void updateUser(ActionEvent event) {
        try{
            FXMLLoader loader = loaderFactory.load("/fxml/updateEmployeeScreen.fxml");

            Parent updateEmployeeScreen = loader.load();
            Scene nextScene = new Scene(updateEmployeeScreen);
            UpdateEmployeeScreenController updateRequestScreenController = loader.getController();
            updateRequestScreenController.setEmployee(employeeService.findById(getCurrentSelection().getId()));
            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){}
    }

    public void addUser(ActionEvent event) {
        try {
            FXMLLoader loader = loaderFactory.load("/fxml/addEmployeeScreen.fxml");

            Parent adminMainScreen = loader.load();

            Scene nextScene = new Scene(adminMainScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }
        catch (Exception e){

        }
    }


    @FXML
    private void clearDeactivationDateButtonClicked(ActionEvent event) {
        deactivationDateDatePicker.setValue(null);
    }
}
