package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.CustomerSelectedEvent;
import pl.polsl.repairmanagementdesktop.LoaderFactory;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

import java.io.IOException;

@Scope("prototype")
@Controller
public class CustomersTabController {


    private ApplicationEventPublisher applicationEventPublisher;
    @FXML
    private TableView<CustomerTableRow> customersTableView;

    private final CustomerRestClient customerRC;
    private final LoaderFactory fxmlLoaderFactory;

    @Autowired
    public CustomersTabController(ApplicationEventPublisher applicationEventPublisher, CustomerRestClient customerRC, LoaderFactory fxmlLoaderFactory) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.customerRC = customerRC;
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }

    private void initCustomerTable(){
        customersTableView.getColumns().clear();

        TableColumn<CustomerTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<CustomerTableRow, String> nameColumn = TableColumnFactory.createColumn("First name", "firstName");
        TableColumn<CustomerTableRow, String>  surnameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        TableColumn<CustomerTableRow, String>  phoneColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
        TableColumn<CustomerTableRow, String> streetColumn = TableColumnFactory.createColumn("Street", "street");
        TableColumn<CustomerTableRow, String> cityColumn = TableColumnFactory.createColumn("City", "city");
        TableColumn<CustomerTableRow, String> postCodeColumn = TableColumnFactory.createColumn("Postal code", "postCode");
        TableColumn<CustomerTableRow, String> numberColumn = TableColumnFactory.createColumn("Number", "number");

        customersTableView.getColumns().addAll(
                idColumn,
                nameColumn,
                surnameColumn,
                phoneColumn,
                cityColumn,
                streetColumn,
                postCodeColumn,
                numberColumn
        );

//        customersTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//                if (newSelection != null){
//                    applicationEventPublisher.publishEvent(new CustomerSelectedEvent(newSelection, source));
//                }
//        });
    }

    @FXML
    public void initialize() {
        initCustomerTable();

    }

    @FXML
    private void addClientButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlLoaderFactory.load("/fxml/addCustomerScreen.fxml");
        Parent managerMainScreen = loader.load();
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

            customersTableView.getItems().add(new CustomerTableRow(customer));

        }
    }


    CustomerTableRow getCurrentSelection() {
        return customersTableView.getSelectionModel().getSelectedItem();
    }
}


