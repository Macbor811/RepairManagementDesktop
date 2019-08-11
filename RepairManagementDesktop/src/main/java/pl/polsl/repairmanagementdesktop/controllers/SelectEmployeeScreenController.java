package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeTableRow;
import pl.polsl.repairmanagementdesktop.utils.search.ConstantParamBinding;

@Scope("prototype")
@Controller
public class SelectEmployeeScreenController {

    @FXML
    private Button selectButton;

    private EmployeeTableRow currentSelection = null;


    @FXML
    public void initialize(){
        employeesTabController.initView();
        employeesTabController.addParamBindings(new ConstantParamBinding("role", "WRK"));
    }


    @FXML
    private EmployeesTabController employeesTabController;

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void selectButtonClicked(ActionEvent event){
        currentSelection = employeesTabController.getCurrentSelection();
        if (currentSelection != null){
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No customer selected!");
        }

    }





    public EmployeeTableRow getCurrentSelection() { return currentSelection; }
}
