package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CustomerSelectedEvent;
import pl.polsl.repairmanagementdesktop.LoaderFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

import java.io.IOException;

@Scope("prototype")
@Controller
public class AddItemScreenController {

    @FXML
    private Pane mainPane;

    private final LoaderFactory fxmlLoaderFactory;
    private final ApplicationEventPublisher eventPublisher;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label currentTypeSelectionLabel;

    @FXML
    private Label currentWorkerSelectionLabel;
    private SelectCustomerScreenController selectCustomerScreenController;
    private Scene selectCustomerScene;


    @Autowired
    public AddItemScreenController(LoaderFactory fxmlLoaderFactory, ApplicationEventPublisher eventPublisher) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.eventPublisher = eventPublisher;

        try {
            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectCustomerScreen.fxml");
            Parent selectCustomerScreen = loader.load();

            selectCustomerScreenController = loader.getController();

            selectCustomerScene = new Scene(selectCustomerScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void selectOwnerButtonClicked(ActionEvent event) throws IOException {

        Scene scene = ((Node) event.getSource()).getScene();
        Stage thisWindow = (Stage) scene.getWindow();

        Stage window = new Stage();


        window.setScene(selectCustomerScene);
        window.setResizable(false);
        thisWindow.hide();
        window.showAndWait();
        thisWindow.show();

        CustomerTableRow customer = selectCustomerScreenController.getSelection();
        if (customer != null){
            currentWorkerSelectionLabel.setText(selectCustomerScreenController.getSelection().getFirstName());

        }

    }


}
