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

@Scope("prototype")
@Controller
public class SelectCustomerScreenController {

    @FXML
    private Button selectButton;

    private Scene previousScene;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @FXML
    private CustomersTabController customersTabController;

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(previousScene);
    }

    @FXML
    private void selectButtonClicked(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        applicationEventPublisher.publishEvent(new CustomerSelectedEvent(customersTabController.getCurrentSelection(), this));
        window.setScene(previousScene);
    }


    void setPreviousScene(Scene scene){
        previousScene = scene;
    }


}
