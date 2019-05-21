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
import pl.polsl.repairmanagementdesktop.Loader;
import pl.polsl.repairmanagementdesktop.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.TextFieldQueryBinding;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerRestClient;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
import uk.co.blackpepper.bowman.Page;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;

@Controller
public class CustomersTabController {

    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

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

    private List<TextFieldQueryBinding> bindings;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;

    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;

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
                postCodeColumn,
                cityColumn,
                streetColumn,
                numberColumn
        );

        for (TableColumn column :  customersTableView.getColumns()){
            column.setStyle("-fx-alignment: CENTER;");
        }

        bindings.addAll(
               new TextFieldQueryBinding(idTextField, "id"),
                new TextFieldQueryBinding(firstNameTextField, "id"),
                new TextFieldQueryBinding(lastNameTextField, "id"),
                new TextFieldQueryBinding(phoneTextField, "id"),
                new TextFieldQueryBinding(streetTextField, "id"),
                new TextFieldQueryBinding(cityTextField, "id"),
                new TextFieldQueryBinding(postCodeTextField, "id"),
                new TextFieldQueryBinding(numberTextField, "id")
        );
    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }

    @FXML
    public void initialize() {
        initCustomersTableView();

        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        rowsPerPageTextField.setTextFormatter(textFormatter);
;    }



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
        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        updateTable();
    }

    private void updateTable(){
        Page<CustomerEntity> page = customerRC.findAll(pagination.getCurrentPageIndex(), rowsPerPage);
        pagination.setPageCount((int) page.getTotalPages());
        customersTableView.getItems().clear();


        for (CustomerEntity customer : page.getResources()){

            customersTableView.getItems().add(new CustomerTableRow(customer));

        }
    }


    private String createQueryStringFromTextFields(){
        String string;

        return string;
    }

}


