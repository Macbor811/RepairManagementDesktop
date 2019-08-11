package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.item.ItemTableRow;

@Scope("prototype")
@Controller
public class SelectItemScreenController {


    @FXML
    private Button selectButton;

    private ItemTableRow currentSelection = null;


    @FXML
    private ItemsTabController itemsTabController;

    @FXML
    public void initialize(){
        itemsTabController.initView();
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void selectButtonClicked(ActionEvent event){
        currentSelection = itemsTabController.getCurrentSelection();
        if (currentSelection != null){
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No request selected!");
        }
    }



    public ItemTableRow getCurrentSelection() {
        return currentSelection;
    }
}
