package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityService;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.search.ConstantParamBinding;

import java.io.IOException;

@Scope("prototype")
@Controller
public class ManageActivitiesScreenController {


    @FXML
    private ActivitiesTabController activitiesTabController;

    private final LoaderFactory loaderFactory;
    private final ActivityService activityService;
    private String requestId;

    public ManageActivitiesScreenController(LoaderFactory loaderFactory, ActivityService activityService) {
        this.loaderFactory = loaderFactory;
        this.activityService = activityService;
    }

    @FXML
    public void initialize(){
        activitiesTabController.initView();
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }


    //TODO: fix fxml
    @FXML
    private void addActivity(ActionEvent event) throws IOException  {
        FXMLLoader loader = loaderFactory.load("/fxml/addActivityScreen.fxml");

        Parent addActivityScreen = loader.load();
        Scene nextScene = new Scene(addActivityScreen);
        AddActivityScreenController addActivityScreenController = loader.getController();
        addActivityScreenController.setRequest(requestId);
        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }


    //TODO: update/rework
    @FXML
    private void finalizeActivity(ActionEvent event){
        ActivityTableRow selection = activitiesTabController.getCurrentSelection();

        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/finalizeActivityScreen.fxml");

                Parent detailsScreen = loader.load();
                FinalizeActivityScreenController dsc = loader.getController();

                dsc.setActivityTableRow(selection);

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

    public void updateActivity(ActionEvent event) {
        try{
        FXMLLoader loader = loaderFactory.load("/fxml/updateActivityScreen.fxml");

        Parent updateActivityScreen = loader.load();
        Scene nextScene = new Scene(updateActivityScreen);
        UpdateActivityScreenController addActivityScreenController = loader.getController();
        addActivityScreenController.setRequest(requestId);
        addActivityScreenController.setActivity(activityService.findById(activitiesTabController.getCurrentSelection().getId().toString()));
        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }catch (IOException e){}

    }



    public void setRequestId(String requestId) {
        this.requestId = requestId;
        activitiesTabController.addParamBindings(new ConstantParamBinding("request.id", requestId));
    }



}
