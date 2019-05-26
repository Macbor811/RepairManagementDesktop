package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.Loader;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.TextFieldQueryCreator;
import pl.polsl.repairmanagementdesktop.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class CustomersTabController {

    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;
    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField numberTextField;

    private final TextFieldQueryCreator queryCreator = new TextFieldQueryCreator();

    @FXML
    private TableView<CustomerTableRow> customersTableView;

    private final CustomerService customerService;
    private final Loader fxmlLoader;
    private String queryString = "";

    @Autowired
    public CustomersTabController(CustomerService customerService, Loader fxmlLoader) {
        this.customerService = customerService;
        this.fxmlLoader = fxmlLoader;
    }

    private void initCustomersTableView() {
        customersTableView.getColumns().clear();

        TableColumn<CustomerTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<CustomerTableRow, String> nameColumn = TableColumnFactory.createColumn("First name", "firstName");
        TableColumn<CustomerTableRow, String> surnameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        TableColumn<CustomerTableRow, String> phoneColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
        TableColumn<CustomerTableRow, String> streetColumn = TableColumnFactory.createColumn("Street", "street");
        TableColumn<CustomerTableRow, String> cityColumn = TableColumnFactory.createColumn("City", "city");
        TableColumn<CustomerTableRow, String> postCodeColumn = TableColumnFactory.createColumn("Postal code", "postCode");
        TableColumn<CustomerTableRow, String> numberColumn = TableColumnFactory.createColumn("Number", "number");

        customersTableView.getColumns().addAll(
                idColumn,
                nameColumn,
                surnameColumn,
                phoneColumn,
                postCodeColumn,
                cityColumn,
                streetColumn,
                numberColumn
        );

        for (TableColumn column : customersTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryTextFields() {
        queryCreator.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(firstNameTextField, "firstName"),
                        new TextFieldParamBinding(lastNameTextField, "lastName"),
                        new TextFieldParamBinding(phoneTextField, "phoneNumber"),
                        new TextFieldParamBinding(streetTextField, "address.street"),
                        new TextFieldParamBinding(cityTextField, "address.city"),
                        new TextFieldParamBinding(postCodeTextField, "address.postCode"),
                        new TextFieldParamBinding(numberTextField, "address.number")
                )
        );
    }


    private void initPagination() {
        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);

        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
    }

    @FXML
    public void initialize() {
        initCustomersTableView();
        initQueryTextFields();
        initPagination();

    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }


    @FXML
    private void addCustomerButtonClicked(ActionEvent event) throws IOException {
        Parent managerMainScreen = fxmlLoader.load("/fxml/addCustomerScreen.fxml");
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    private void showCustomersButtonClicked() {
        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        queryString = queryCreator.createQueryString();
        updateTable();
    }

    private void updateTable() {
        try{
            Page<CustomerEntity> page = customerService.findAllMatching(queryString, pagination.getCurrentPageIndex(), rowsPerPage);
            pagination.setPageCount((int) page.getTotalPages());
            customersTableView.getItems().clear();


            for (CustomerEntity customer : page.getResources()) {

                customersTableView.getItems().add(new CustomerTableRow(customer));

            }
        } catch (ResourceAccessException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }

    }

//    @ExceptionHandler(ResourceAccessException.class)
//    public void handleException(ResourceAccessException e) {
//        System.out.println(e.getMessage());
//    }

}


