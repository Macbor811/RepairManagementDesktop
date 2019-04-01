package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerDTO;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;

import java.io.IOException;

@Controller
public class ManagerMainScreenController {


    @FXML
    private TableView<CustomerDTO> customersTableView;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button showCustomersButton;

    private final CustomerRestClient customerRC;

    public ManagerMainScreenController(CustomerRestClient customerRC) {
        this.customerRC = customerRC;
    }


    @FXML
    public void initialize() {

        customersTableView.getColumns().clear();

        TableColumn<CustomerDTO, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CustomerDTO, String> nameColumn = new TableColumn<>("First name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<CustomerDTO, String>  surnameColumn = new TableColumn<>("Last name");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        customersTableView.getColumns().addAll(idColumn, nameColumn, surnameColumn);



    }



    @FXML
    private void addClientButtonClicked(ActionEvent event) throws IOException{
        Parent managerMainScreen = FXMLLoader.load(getClass().getResource("/addCustomerScreen.fxml"));
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    private void showCustomersButtonClicked(){
        customersTableView.getItems().clear();
        for (CustomerDTO customer : customerRC.findAll()){
            customersTableView.getItems().add(customer);
        }
    }
}

