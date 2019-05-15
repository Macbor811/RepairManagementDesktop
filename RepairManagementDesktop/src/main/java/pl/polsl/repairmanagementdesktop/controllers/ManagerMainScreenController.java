package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.Loader;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.model.address.AddressEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

import java.io.IOException;

@Controller
public class ManagerMainScreenController {


    //@FXML
    //private Tab customersTab;

    @FXML
    private CustomersTabController tabController;

//    public ManagerMainScreenController(CustomerRestClient customerRC, Loader fxmlLoader) {
//        this.customerRC = customerRC;
//        this.fxmlLoader = fxmlLoader;
//    }




}

