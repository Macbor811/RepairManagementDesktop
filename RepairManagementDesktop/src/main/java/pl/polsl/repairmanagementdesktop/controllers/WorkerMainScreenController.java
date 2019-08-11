package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CurrentUser;
import pl.polsl.repairmanagementdesktop.utils.search.ConstantParamBinding;


@Controller
public class WorkerMainScreenController {

    //private Integer currentUserId = 2;

    @Autowired
    private CurrentUser currentUser;

    @FXML
    private AnchorPane activitiesTab;

    @FXML
    private ActivitiesTabController activitiesTabController;


    @FXML
    public void initialize(){
        //add filter so he can only view his own activities
        activitiesTabController.addParamBindings(new ConstantParamBinding("worker.id", currentUser.getId().toString()));
        activitiesTabController.initView();

        fileMenu.getItems().clear();
        var logoutItem = new MenuItem("Sign out");
        fileMenu.getItems().add(logoutItem);

        logoutItem.setOnAction((event) -> {currentUser.signOut((Stage) activitiesTab.getScene().getWindow());});
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
    private Menu fileMenu;


}
