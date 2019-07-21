package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CurrentUser;

import java.io.IOException;

@Controller
public class ManagerMainScreenController {


    public Tab socialUsersPaneTab;
    public Tab activitiesPaneTab;
    public Tab requestsPaneTab;
    public Tab itemsPaneTab;
    @FXML
    private Button createRequestButton;

    @FXML
    private CustomersTabController customersTabController;
    @FXML
    private ItemsTabController itemsTabController;
    @FXML
    private RequestsTabController requestsTabController;
    @FXML
    private ActivitiesTabController activitiesTabController;
    @FXML
    private SocialUsersTabController socialUsersTabController;

    @Autowired
    private CurrentUser currentUser;

    @FXML
    private Menu fileMenu;

    @FXML
    public void initialize(){
        fileMenu.getItems().clear();
        var logoutItem = new MenuItem("Sign out");
        fileMenu.getItems().add(logoutItem);

        logoutItem.setOnAction((event) -> {currentUser.signOut((Stage) createRequestButton.getScene().getWindow());});
    }

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
    private void manageRequestActivitiesButtonClicked(ActionEvent event) throws IOException {
        requestsTabController.manageRequestActivities(event);

    }

    @FXML
    private Tab customersPaneTab;

    @FXML
    private void customersTabSelected(Event event) {
        if (customersPaneTab.isSelected()){
            customersTabController.initView();
        }
    }

    public void itemsTabSelected(Event event) {
        if (itemsPaneTab.isSelected()){
            itemsTabController.initView();
        }
    }

    public void requestsTabSelected(Event event) {
        if (requestsPaneTab.isSelected()){
            requestsTabController.initView();
        }

    }

    public void activitiesTabSelected(Event event) {
        if (activitiesPaneTab.isSelected()){
            activitiesTabController.initView();
        }
    }

    public void socialUsersTabSelected(Event event) {
        if (socialUsersPaneTab.isSelected()){
            socialUsersTabController.initView();
        }
    }
}

