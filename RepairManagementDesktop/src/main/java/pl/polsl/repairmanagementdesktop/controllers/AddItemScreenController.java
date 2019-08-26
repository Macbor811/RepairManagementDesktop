package pl.polsl.repairmanagementdesktop.controllers;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemService;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeService;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Scope("prototype")
@Controller
public class AddItemScreenController {



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

    private final ItemTypeService itemTypeService;
    private final ItemService itemService;
    private final CustomerService customerService;



    private SelectCustomerScreenController selectCustomerScreenController;
    private Scene selectCustomerScene;
    private CustomerTableRow ownerTableRow;


    @Autowired
    public AddItemScreenController(LoaderFactory fxmlLoaderFactory, ItemTypeService itemTypeService, ItemService itemService, CustomerService customerService) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.itemTypeService = itemTypeService;
        this.itemService = itemService;
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

    private void initItemTypeListView(){
        itemTypeListView.setItems(null);
        ObservableList<String> itemTypes = FXCollections
                .observableList(
                        itemTypeService
                                .findAll(0, Integer.MAX_VALUE)
                                .getResources()
                                .stream()
                                .map(entity -> {
                                    String uriString = entity.getUri().toString();
                                    return uriString.substring(uriString.lastIndexOf("/") + 1); //extract ID from URI
                                })
                                .collect(Collectors.toList())
                );


        itemTypeListView.setItems(itemTypes);
    }

    @FXML
    public void initialize(){

        initItemTypeListView();
        TextFieldUtils.setMaxLength(itemNameTextField, 255);
        TextFieldUtils.setMaxLength(itemTypeTextField, 50);
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

        var selectedType = (String) itemTypeListView.getSelectionModel().getSelectedItem();

        var itemNameText = itemNameTextField.getText();

        if (selectedType != null && ownerTableRow != null && itemNameText!= null && !itemNameText.isEmpty()){
            ItemTypeEntity type = itemTypeService.findById(selectedType);
            var ownerId = ownerTableRow.getId();
            CustomerEntity owner = customerService.findById(ownerId);

            ItemEntity item = new ItemEntity(itemNameText, type, owner);

            itemService.save(item);


            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        } else {
            messageLabel.setText("All data must be set.");
        }

    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void addItemTypeButtonClicked(ActionEvent event) {
        var type = new ItemTypeEntity();
        var text = itemTypeTextField.getText();
        if (Pattern.compile("\\s").matcher(text).find()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Type name can not contain any whitespaces.");
            errorAlert.show();
        } else {
            type.setType(text);
            itemTypeService.save(type);
            initItemTypeListView();
        }

    }
}
