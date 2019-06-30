package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public class AdminMainScreenController {

    @FXML
    private EmployeesTabController employeesTabController;

    @FXML
    private void addUserButtonClicked(ActionEvent event){
        employeesTabController.addUser(event);
    }

    @FXML
    private void updateUserButtonClicked(ActionEvent event){
        employeesTabController.updateUser(event);
    }

}
