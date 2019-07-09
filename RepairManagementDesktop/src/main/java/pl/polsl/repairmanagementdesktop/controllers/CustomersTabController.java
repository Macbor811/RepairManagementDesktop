package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.utils.*;
import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Scope("prototype")
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

    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();


    @FXML
    private TableView<CustomerTableRow> customersTableView;

    private final CustomerService customerService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public CustomersTabController(CustomerService customerService, LoaderFactory loaderFactory) {
        this.customerService = customerService;
        this.loaderFactory = loaderFactory;
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

        for (var column : customersTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        customersTableView.setColumnResizePolicy((param) -> true );
    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
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
        initQueryFields();
        initPagination();

    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }


    public void addParamBindings(ParamBinding... bindings){
        for (var binding : bindings){
            uriSearchQuery.getBindings().add(binding);
        }
        uriSearchQuery.update();
    }

    @FXML
    private void addCustomerButtonClicked(ActionEvent event) throws IOException {
        Parent managerMainScreen = loaderFactory.load("/fxml/addCustomerScreen.fxml").load();
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    public void updateCustomer(ActionEvent event) {
        try{
            FXMLLoader loader = loaderFactory.load("/fxml/updateCustomerScreen.fxml");

            Parent updateCustomerScreen = loader.load();
            Scene nextScene = new Scene(updateCustomerScreen);
            UpdateCustomerScreenController addCustomerScreenController = loader.getController();
            addCustomerScreenController.setCustomer(customerService.findById(getCurrentSelection().getId()));
            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        } catch (IOException e){}

    }

    /**
     * Updates search settings from text fields to show new results.
     */
    @FXML
    private void showCustomersButtonClicked() {


        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        uriSearchQuery.update();
        updateTable();

    }

    @Autowired
    ThreadPoolExecutor executor;// = (ThreadPoolExecutor ) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    private void updateTable() {
        executor.submit(() -> {
            try{

                Page<CustomerEntity> page = customerService.findAllMatching(uriSearchQuery, pagination.getCurrentPageIndex(), rowsPerPage);

                pagination.setPageCount((int) page.getTotalPages());


                customersTableView.getItems().setAll(page.getResources().stream().map(CustomerTableRow::new).collect(Collectors.toList()));

            } catch (ResourceAccessException e){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Connection error");
                errorAlert.setContentText(e.getMessage());
                errorAlert.show();
            }
        });


    }



    CustomerTableRow getCurrentSelection(){
        return customersTableView.getSelectionModel().getSelectedItem();
    }


}


