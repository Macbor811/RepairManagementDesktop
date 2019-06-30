package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.springframework.stereotype.Controller;

@Controller
public class ManagerMainScreenController {

    @FXML
    private CustomersTabController customersTabController;
    @FXML
    private ItemsTabController itemsTabController;
    @FXML
    private RequestsTabController requestsTabController;
    @FXML
    private ActivitiesTabController activitiesTabController;





    @FXML
    private void createActivityButtonClicked(ActionEvent event) {
        activitiesTabController.createActivity(event);
    }

    @FXML
    private void updateActivityButtonClicked(ActionEvent event) {
        activitiesTabController.updateActivity(event);
    }

    @FXML
    private void finalizeActivityButtonClicked(ActionEvent event) {
        activitiesTabController.finalizeActivity(event);
    }

    public void updateRequestButtonClicked(ActionEvent event) {
    }

    public void addRequestButtonClicked(ActionEvent event) {
    }

    public void finalizeRequestButtonClicked(ActionEvent event) {
    }

//    public ManagerMainScreenController(CustomerRestClient customerRC, Loader fxmlLoader) {
//        this.customerRC = customerRC;
//        this.fxmlLoader = fxmlLoader;
//    }




}

