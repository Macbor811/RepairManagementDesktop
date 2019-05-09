package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.Loader;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;

import java.io.IOException;

@Controller
public class ManagerMainScreenController {


    @FXML
    private TableView<CustomerEntity> customersTableView;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button showCustomersButton;

    private final CustomerRestClient customerRC;
    private final Loader fxmlLoader;

    public ManagerMainScreenController(CustomerRestClient customerRC, Loader fxmlLoader) {
        this.customerRC = customerRC;
        this.fxmlLoader = fxmlLoader;
    }




    private void initCustomerTable(){
        customersTableView.getColumns().clear();

        //TableColumn<CustomerEntity, Integer> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<CustomerEntity, String> nameColumn = TableColumnFactory.createColumn("First name", "firstName");
        TableColumn<CustomerEntity, String>  surnameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        TableColumn<CustomerEntity, String>  phoneColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
       // TableColumn<CustomerDTO, String> streetColumn = TableColumnFactory.createColumn("Street", "street");
        //TableColumn<CustomerDTO, String> cityColumn = TableColumnFactory.createColumn("City", "city");
        //TableColumn<CustomerDTO, String> postCodeColumn = TableColumnFactory.createColumn("Postal code", "postCode");
        //TableColumn<CustomerDTO, String> numberColumn = TableColumnFactory.createColumn("Number", "number");

        customersTableView.getColumns().addAll(
                //idColumn,
                nameColumn,
                surnameColumn,
                phoneColumn
                //cityColumn,
                //streetColumn,
                //postCodeColumn,
                //numberColumn
        );
    }

    @FXML
    public void initialize() {
        initCustomerTable();

    }

    @FXML
    private void addClientButtonClicked(ActionEvent event) throws IOException{
        Parent managerMainScreen = fxmlLoader.load("/addCustomerScreen.fxml");
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    private void showCustomersButtonClicked(){
        customersTableView.getItems().clear();
        for (CustomerEntity customer : customerRC.findAll()){
            customersTableView.getItems().add(customer);
        }
    }
}

