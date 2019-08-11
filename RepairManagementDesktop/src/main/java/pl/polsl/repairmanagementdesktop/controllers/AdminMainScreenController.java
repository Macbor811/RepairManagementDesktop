package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CurrentUser;

@Scope("prototype")
@Controller
public class AdminMainScreenController {

    @FXML
    private Button addUserButton;
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


    @FXML
    private Menu fileMenu;

    @Autowired
    private CurrentUser currentUser;

    @FXML
    public void initialize(){
        employeesTabController.initView();
        fileMenu.getItems().clear();
        var logoutItem = new MenuItem("Sign out");
        fileMenu.getItems().add(logoutItem);

        logoutItem.setOnAction((event) -> {currentUser.signOut((Stage) addUserButton.getScene().getWindow());});
    }
}
