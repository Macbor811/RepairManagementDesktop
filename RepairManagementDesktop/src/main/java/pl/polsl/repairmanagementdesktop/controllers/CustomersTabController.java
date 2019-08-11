package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.abstr.TabController;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import pl.polsl.repairmanagementdesktop.utils.*;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;

import java.io.IOException;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class CustomersTabController extends TabController<CustomerEntity, CustomerTableRow> {


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



    private final CustomerService customerService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public CustomersTabController(CustomerService customerService, LoaderFactory loaderFactory) {
        super(customerService, CustomerTableRow::new);
        this.customerService = customerService;
        this.loaderFactory = loaderFactory;
    }


    @Override
    protected void initTableView() {
        tableView.getColumns().clear();

        TableColumn<CustomerTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        idColumn.setPrefWidth(40);
        TableColumn<CustomerTableRow, String> nameColumn = TableColumnFactory.createColumn("First name", "firstName");
        nameColumn.setPrefWidth(100);
        TableColumn<CustomerTableRow, String> surnameColumn = TableColumnFactory.createColumn("Last name", "lastName");
        surnameColumn.setPrefWidth(100);
        TableColumn<CustomerTableRow, String> phoneColumn = TableColumnFactory.createColumn("Phone", "phoneNumber");
        TableColumn<CustomerTableRow, String> streetColumn = TableColumnFactory.createColumn("Street", "street");
        streetColumn.setPrefWidth(100);
        TableColumn<CustomerTableRow, String> cityColumn = TableColumnFactory.createColumn("City", "city");
        cityColumn.setPrefWidth(100);
        TableColumn<CustomerTableRow, String> postCodeColumn = TableColumnFactory.createColumn("Postal code", "postCode");
        TableColumn<CustomerTableRow, String> numberColumn = TableColumnFactory.createColumn("Number", "number");

        tableView.getColumns().addAll(
                idColumn,
                nameColumn,
                surnameColumn,
                phoneColumn,
                postCodeColumn,
                cityColumn,
                streetColumn,
                numberColumn
        );

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

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

}


