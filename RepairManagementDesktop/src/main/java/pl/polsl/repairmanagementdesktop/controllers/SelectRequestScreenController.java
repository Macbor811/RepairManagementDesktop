package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;

@Scope("prototype")
@Controller
public class SelectRequestScreenController {

    @FXML
    private Button selectButton;

    private RequestTableRow currentSelection = null;

    @FXML
    private RequestsTabController requestsTabController;

    @FXML
    public void initialize(){
        requestsTabController.initView();
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void selectButtonClicked(ActionEvent event){
        currentSelection = requestsTabController.getCurrentSelection();
        if (currentSelection != null){
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No request selected!");
        }
    }

    public RequestTableRow getCurrentSelection() {
        return currentSelection;
    }
}
