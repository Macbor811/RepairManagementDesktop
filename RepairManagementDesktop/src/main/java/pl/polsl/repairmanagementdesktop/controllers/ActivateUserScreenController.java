package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserEntity;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserService;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;

@Scope("prototype")
@Controller
public class ActivateUserScreenController {

    @FXML
    private TextField itemTypeTextField;

    @FXML
    private Label currentOwnerSelectionLabel;
    @FXML
    private TextField itemNameTextField;
    @FXML
    private ListView itemTypeListView;
    @FXML
    private Label messageLabel;

    private final LoaderFactory fxmlLoaderFactory;

    private final SocialUserService socialUserService;


    private final CustomerService customerService;

    @FXML
    private TextField descriptionTextField;


    private SelectCustomerScreenController selectCustomerScreenController;
    private Scene selectCustomerScene;
    private  CustomerTableRow ownerTableRow;

    private SocialUserEntity user;


    @Autowired
    public ActivateUserScreenController(LoaderFactory fxmlLoaderFactory, SocialUserService socialUserService, CustomerService customerService) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.socialUserService = socialUserService;

        this.customerService = customerService;

        //prepare customer selection screen
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
        window.showAndWait(); //wait for results from SelectCustomerScreen
        thisWindow.show();

        ownerTableRow = selectCustomerScreenController.getCurrentSelection();
        if (ownerTableRow != null){
            currentOwnerSelectionLabel.setText(ownerTableRow.getFirstName() + " " + ownerTableRow.getLastName());
        }

    }

    @FXML
    private void addItemButtonClicked(ActionEvent event) {

        CustomerEntity owner = customerService.findById(ownerTableRow.getId());

        user.setCustomer(owner);

        socialUserService.update(user, owner);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void setUser(SocialUserTableRow selection) {
        user = socialUserService.findById(selection.getId());
    }
}
