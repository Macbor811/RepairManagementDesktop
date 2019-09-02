package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.utils.auth.CurrentUser;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.search.ConstantParamBinding;

import java.io.IOException;

@Controller
public class WorkerMainScreenController {

    @FXML
    private Button updateButton;
    @FXML
    private Button requestDetailsButton;

    private final CurrentUser currentUser;

    @FXML
    private AnchorPane activitiesTab;
    private final ActivityService service;

    @FXML
    private ActivitiesTabController activitiesTabController;

    private final LoaderFactory loaderFactory;

    @Autowired
    public WorkerMainScreenController(CurrentUser currentUser, ActivityService service, LoaderFactory loaderFactory) {
        this.currentUser = currentUser;
        this.service = service;
        this.loaderFactory = loaderFactory;
    }

    @FXML
    public void initialize(){
        //add filter so he can only view his own activities
        activitiesTabController.addParamBindings(new ConstantParamBinding("worker.id", currentUser.getId().toString()));
        activitiesTabController.setWorkerMode(true);
        activitiesTabController.initView();
        activitiesTabController.bindDisableToSelection(requestDetailsButton, updateButton);

        fileMenu.getItems().clear();
        var logoutItem = new MenuItem("Sign out");
        fileMenu.getItems().add(logoutItem);

        logoutItem.setOnAction((event) -> {currentUser.signOut((Stage) activitiesTab.getScene().getWindow());});
    }

    @FXML
    private void updateActivityButtonClicked(ActionEvent event) {
        ActivityTableRow selection = activitiesTabController.getCurrentSelection();
        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/updateActivityWorkerScreen.fxml");

                Parent detailsScreen = loader.load();
                UpdateActivityWorkerScreenController dsc = loader.getController();
                dsc.setActivityData(selection);

                Scene nextScene = new Scene(detailsScreen);

                Stage window = new Stage();

                window.setScene(nextScene);
                window.setResizable(false);
                window.show();
            }
            catch (IOException e)
            {}
        }
    }



    @FXML
    private Menu fileMenu;


    @FXML
    private void requestDetailsButtonClicked(ActionEvent event) {
        ActivityTableRow selection = activitiesTabController.getCurrentSelection();

        if (selection != null){
            try
            {
                var activity = service.findById(selection.getId().toString());
                FXMLLoader loader = loaderFactory.load("/fxml/detailsScreen.fxml");

                Parent detailsScreen = loader.load();
                DetailsScreenController dsc = loader.getController();

                dsc.setRequest(activity.getRequest());

                Scene nextScene = new Scene(detailsScreen);

                Stage window = new Stage();

                window.setScene(nextScene);
                window.setResizable(false);
                window.show();
            }
            catch (IOException e)
            {}
        }
    }
}
