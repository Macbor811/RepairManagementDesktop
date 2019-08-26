package pl.polsl.repairmanagementdesktop.controllers;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.abstr.TabController;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerService;
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemService;
import pl.polsl.repairmanagementdesktop.model.item.ItemTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
import pl.polsl.repairmanagementdesktop.utils.search.SupplierBasedParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;

import java.io.IOException;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class ItemsTabController extends TabController<ItemEntity, ItemTableRow> {


    private final LoaderFactory fxmlLoaderFactory;
    @FXML
    private Button updateButton;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField customerTextField;



    private final ItemService itemService;
    private final CustomerService customerService;

    @Autowired
    public ItemsTabController(ItemService itemService, LoaderFactory fxmlLoaderFactory, CustomerService customerService) {
        super(itemService, ItemTableRow::new);
        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.itemService = itemService;
        this.customerService = customerService;
    }



    @Override
    protected void initTableView() {
        bindDisableToSelection(updateButton);
        tableView.getColumns().clear();

        TableColumn<ItemTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ItemTableRow, String> nameColumn = TableColumnFactory.createColumn("Name", "name");
        nameColumn.setPrefWidth(100);
        TableColumn<ItemTableRow, String> typeColumn = TableColumnFactory.createColumn("Type", "type");
        typeColumn.setPrefWidth(100);
        TableColumn<ItemTableRow, String> clientColumn = TableColumnFactory.createColumn("Client", "client");
        clientColumn.setPrefWidth(130);

        tableView.getColumns().addAll(
                idColumn,
                nameColumn,
                typeColumn,
                clientColumn
        );

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private String customerId = "";

    @FXML
    private void clearCustomerFieldButtonClicked(ActionEvent actionEvent) {
        customerTextField.clear();
        customerTextField.setEditable(true);
        customerId = "";
    }


    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(nameTextField, "name"),
                        new TextFieldParamBinding(typeTextField, "type.type"),
                        new SupplierBasedParamBinding("owner.id", () -> customerId)
                )
        );

        var autoCompletion = new AutoCompletionTextFieldBinding<>(customerTextField, t -> customerService.findByFullName(t.getUserText()));
        autoCompletion.setOnAutoCompleted(t -> {
            customerId = t.getCompletion().substring(t.getCompletion().lastIndexOf(" ") + 1);
            customerTextField.setEditable(false);
        });
    }




    @FXML
    private void updateItemsButtonClicked() {
        try{
            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/updateItemScreen.fxml");

            Parent updateItemScreen = loader.load();
            Scene nextScene = new Scene(updateItemScreen);
            UpdateItemScreenController addItemScreenController = loader.getController();
            addItemScreenController.setItem(itemService.findById(getCurrentSelection().getId()));
            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){}

    }

    @FXML
    private void addItemButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlLoaderFactory.load("/fxml/addItemScreen.fxml");

        Parent managerMainScreen = loader.load();

        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }



}
