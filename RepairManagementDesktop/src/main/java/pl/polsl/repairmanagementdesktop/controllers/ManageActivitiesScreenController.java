package pl.polsl.repairmanagementdesktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button updateButton;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;

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
        var table = activitiesTabController.getTableView();
        ReadOnlyIntegerProperty selectedIndex = table.getSelectionModel().selectedIndexProperty();

        upButton.disableProperty().bind(selectedIndex.lessThanOrEqualTo(0));
        downButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            int index = selectedIndex.get();
            return index < 0 || index+1 >= table.getItems().size();
        }, selectedIndex, table.getItems()));
        updateButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

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

    @FXML
    private void updateActivity(ActionEvent event){
        ActivityTableRow selection = activitiesTabController.getCurrentSelection();

        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/updateActivityScreen.fxml");

                Parent detailsScreen = loader.load();
                UpdateActivityScreenController dsc = loader.getController();

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


    public void setRequestId(String requestId) {
        this.requestId = requestId;
        activitiesTabController.addParamBindings(new ConstantParamBinding("request.id", requestId));
    }


    @FXML
    private void moveActivityUp(ActionEvent event) {
        activitiesTabController.moveSelected(1);
    }

    @FXML
    private void moveActivityDown(ActionEvent event) {
        activitiesTabController.moveSelected(-1);
    }
}
