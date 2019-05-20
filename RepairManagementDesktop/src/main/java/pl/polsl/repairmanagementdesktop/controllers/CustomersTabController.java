package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.Loader;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;

import java.io.IOException;

@Controller
public class CustomersTabController {

    private static final Integer DEFAULT_ROWS_PER_PAGE_COUNT = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;


    @FXML
    private TableView<CustomerTableRow> customersTableView;

    private final CustomerRestClient customerRC;
    private final Loader fxmlLoader;

    @Autowired
    public CustomersTabController(CustomerRestClient customerRC, Loader fxmlLoader) {
        this.customerRC = customerRC;
        this.fxmlLoader = fxmlLoader;
    }

    private void initCustomersTableView(){
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
    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        showCustomersButtonClicked();

        return new Pane(); //dummy value, not used
    }

    @FXML
    public void initialize() {
        initCustomersTableView();

        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE_COUNT.toString());
        pagination.setPageFactory(this::createPage);
    }

    @FXML
    private void addClientButtonClicked(ActionEvent event) throws IOException {
        Parent managerMainScreen = fxmlLoader.load("/fxml/addCustomerScreen.fxml");
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    private void showCustomersButtonClicked(){
        customersTableView.getItems().clear();
        for (CustomerEntity customer :
                customerRC.findAll(pagination.getCurrentPageIndex(), Integer.valueOf(rowsPerPageTextField.getText()))){

            customersTableView.getItems().add(new CustomerTableRow(customer));

        }
    }




}


