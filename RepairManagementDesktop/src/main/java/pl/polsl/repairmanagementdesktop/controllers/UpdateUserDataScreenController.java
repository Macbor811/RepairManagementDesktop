package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.utils.auth.CurrentUser;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeRole;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeUserDataDto;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;

import java.time.ZoneId;

@Controller
@Scope("prototype")
public class UpdateUserDataScreenController{

    @FXML
    private DatePicker deactivationDatePicker;
    @FXML
    private ChoiceBox<EmployeeRole> roleChoiceBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button cancelEmployeeButton;
    @FXML
    private Label messageLabel;


    private final EmployeeService employeeService;
    private final CurrentUser currentUser;

    private EmployeeEntity employee;
    private Stage mainWindow;

    @FXML
     void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @Autowired
    public UpdateUserDataScreenController(EmployeeService employeeService, CurrentUser currentUser) {
        this.employeeService = employeeService;
        this.currentUser = currentUser;
    }


    public void setEmployee(EmployeeEntity entity){
        this.employee = entity;
        TextFieldUtils.setMaxLength(usernameTextField, 50);
        TextFieldUtils.setMaxLength(passwordField, 60);

        usernameTextField.setText(entity.getUsername());
        if (employee.getDeactivationDate() != null)
        deactivationDatePicker.setValue(employee.getDeactivationDate().atZone(ZoneId.systemDefault()).toLocalDate());

        roleChoiceBox.getItems().addAll(
                EmployeeRole.values()
        );

        roleChoiceBox.getSelectionModel().select(EmployeeRole.fromString(entity.getRole()));

    }

    public void setMainWindow(Stage mainWindow){
        this.mainWindow = mainWindow;
    }

    @FXML
    private void updateEmployeeButtonClicked(ActionEvent event) {
        var data = new EmployeeUserDataDto(
                deactivationDatePicker.getValue() != null ? deactivationDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant() : null,
                passwordField.getText()
        );
        employeeService.update(employee.getUri(), data);

        if (employee.getUsername().equals(currentUser.getUsername())){
            currentUser.signOut(mainWindow);
        } else{
            cancelButtonClicked(event);
        }
    }


}
