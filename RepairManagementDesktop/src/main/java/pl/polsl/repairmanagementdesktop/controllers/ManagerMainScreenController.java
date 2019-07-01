package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.springframework.stereotype.Controller;

import java.io.IOException;

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
        try
        {
        activitiesTabController.addActivity(event);
        }
        catch(IOException e)
        {


        }
    }

    @FXML
    private void updateActivityButtonClicked(ActionEvent event) {
        activitiesTabController.updateActivity(event);
    }

    @FXML
    private void finalizeActivityButtonClicked(ActionEvent event) {
        activitiesTabController.finalizeActivity(event);
    }

    @FXML
    private void showActivityDetailsButtonClicked(ActionEvent event) {
        activitiesTabController.showActivityDetails(event);
    }

    @FXML
    private void updateRequestButtonClicked(ActionEvent event) {
        requestsTabController.updateRequest(event);
    }

    @FXML
    private void addRequestButtonClicked(ActionEvent event) {
        try{
        requestsTabController.addRequest(event);
        }
        catch(IOException e)
        {


        }

    }

    @FXML
    private void finalizeRequestButtonClicked(ActionEvent event) {
        requestsTabController.finalizeRequest(event);
    }


    @FXML
    private void showRequestDetailsButtonClicked(ActionEvent event) {
        requestsTabController.showRequestDetails(event);
    }

    @FXML
    private void manageRequestActivitiesButtonClicked(ActionEvent event) {
        requestsTabController.manageRequestActivities(event);

    }




}

