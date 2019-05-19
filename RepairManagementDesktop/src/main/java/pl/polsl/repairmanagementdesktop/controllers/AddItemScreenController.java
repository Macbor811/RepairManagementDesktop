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

    private FXMLLoader thisLoader;
    private final LoaderFactory fxmlLoaderFactory;
    private final ApplicationEventPublisher eventPublisher;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label currentTypeSelectionLabel;

    @FXML
    private Label currentWorkerSelectionLabel;

    @FXML
    private SelectCustomerScreenController selectCustomerScreenController;
    @FXML
    private Node selectCustomerScreen;

    @Autowired
    public AddItemScreenController(LoaderFactory fxmlLoaderFactory, ApplicationEventPublisher eventPublisher) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.eventPublisher = eventPublisher;
    }

//    @FXML
//    public void initialize(){
//        try {
//            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectCustomerScreen.fxml");
//            Parent selectCustomerScreen = loader.load();
//
//            selectCustomerScreenController = loader.getController();
//
//
//
//            selectCustomerScene = new Scene(selectCustomerScreen);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void selectOwnerButtonClicked(ActionEvent event) throws IOException {

        Scene scene = ((Node) event.getSource()).getScene();
        Stage window = (Stage) scene.getWindow();
        window.setResizable(true);

        mainPane.getChildren().setAll(selectCustomerScreen);
        window.sizeToScene();
        mainPane.resize(selectCustomerScreen.prefWidth(-1), selectCustomerScreen.prefHeight(-1));
//
//        selectCustomerScreenController.setPreviousScene(((Node) event.getSource()).getScene());
        //thisLoader.setRoot(selectCustomerScreen);
//        window.setResizable(true);
//        window.centerOnScreen();//SelectCustomerScreenController selectionController = loader.getController();
//        //window.getScene().setRoot(fxmlLoaderFactory.load("fxml/selectCustomerScreen.fxml").load());

    }



    @EventListener
    public void ownerSelected(CustomerSelectedEvent event){
        if (event.getSource() == selectCustomerScreenController){
            CustomerTableRow customer = event.getCustomer();
            currentWorkerSelectionLabel.setText(event.getCustomer().getFirstName() +  " " + event.getCustomer().getLastName());
            //currentTypeSelectionLabel.setText(descriptionTextField.getText());
        }
//        else {
//            eventPublisher.publishEvent(event);
//        }


    }


    public void setThisLoader(FXMLLoader thisLoader) {
        this.thisLoader = thisLoader;
    }
}
