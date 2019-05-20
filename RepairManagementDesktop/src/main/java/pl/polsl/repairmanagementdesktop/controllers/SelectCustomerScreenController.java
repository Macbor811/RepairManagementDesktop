package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CustomerSelectedEvent;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

@Scope("prototype")
@Controller
public class SelectCustomerScreenController {

    @FXML
    private Button selectButton;


    @FXML
    private CustomersTabController customersTabController;

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void selectButtonClicked(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    CustomerTableRow getSelection(){
        return customersTabController.getCurrentSelection();
    }


}
