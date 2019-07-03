package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeService;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemService;
import pl.polsl.repairmanagementdesktop.model.item.ItemTableRow;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;
import java.time.Instant;

@Scope("prototype")
@Controller
public class UpdateRequestScreenController {
        @FXML
        private Button selectItemButton;
        @FXML
        private Button addActivityButton;
        @FXML
        private Button cancelActivityButton;
        @FXML
        private Label currentItemSelectionLabel;
        @FXML
        private TextField descriptionTextField;


        private final LoaderFactory fxmlLoaderFactory;

        private final ItemService itemService;
        private final RequestService requestService;

        private SelectItemScreenController selectItemScreenController;
        private Scene selectItemScene;
        private ItemTableRow itemTableRow;
        private RequestTableRow requestTableRow;

        private EmployeeService employeeService;

        @Autowired
        public UpdateRequestScreenController(LoaderFactory fxmlLoaderFactory, RequestService requestService, ItemService itemService, EmployeeService employeeService) {
            this.fxmlLoaderFactory = fxmlLoaderFactory;
            this.requestService = requestService;
            this.itemService = itemService;
            this.employeeService = employeeService;

            //prepare item selection screen
            try {
                FXMLLoader loader = fxmlLoaderFactory.load("/fxml/selectItemScreen.fxml");
                Parent selectItemScreen = loader.load();

                selectItemScreenController = loader.getController();

                selectItemScene = new Scene(selectItemScreen);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @FXML
        public void initialize(){

        }


        @FXML
        private void selectItemButtonClicked(ActionEvent event) throws IOException {

            Scene scene = ((Node) event.getSource()).getScene();
            Stage thisWindow = (Stage) scene.getWindow();

            Stage window = new Stage();


            window.setScene(selectItemScene);
            window.setResizable(false);
            thisWindow.hide();
            window.showAndWait(); //wait for results from SelectCustomerScreen
            thisWindow.show();

            itemTableRow = selectItemScreenController.getCurrentSelection();
            if (itemTableRow != null) {
                currentItemSelectionLabel.setText(itemTableRow.getId());
            }

        }

        @FXML
        private void updateRequestButtonClicked(ActionEvent event) {
            ItemEntity ie = itemService.findById(itemTableRow.getId());
            EmployeeEntity ee = employeeService.findById("1");

            RequestEntity request = new RequestEntity(descriptionTextField.getText(),"","OPN", Instant.now(),Instant.now(),null,ie,ee);

           // requestService.save(request);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();

        }

        @FXML
        private void cancelButtonClicked(ActionEvent event) {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        }

    public void setRequest(RequestEntity request) {
        String id = request.getItem().getUri().toString().substring(request.getItem().getUri().toString().lastIndexOf("/") + 1);
        currentItemSelectionLabel.setText(id);
        descriptionTextField.setText(request.getDescription());

        itemTableRow = new ItemTableRow( itemService.findById(id));

    }
}