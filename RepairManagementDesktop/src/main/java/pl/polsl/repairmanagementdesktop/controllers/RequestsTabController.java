package pl.polsl.repairmanagementdesktop.controllers;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.Stage;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Scope;
        import org.springframework.stereotype.Controller;
        import pl.polsl.repairmanagementdesktop.abstr.TabController;
        import pl.polsl.repairmanagementdesktop.model.customer.CustomerTableRow;
        import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
        import pl.polsl.repairmanagementdesktop.model.request.RequestService;
        import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
        import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
        import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
        import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
        import pl.polsl.repairmanagementdesktop.utils.search.*;

        import java.io.IOException;
        import java.util.Arrays;
        import java.util.StringJoiner;

@Scope("prototype")
@Controller
public class RequestsTabController extends TabController<RequestEntity, RequestTableRow> {


    @FXML
    private Label selectedCustomerLabel;
    @FXML
    private Label selectedItemLabel;
    @FXML
    private TextField resultTextField;
    @FXML
    private Label selectedStatusesLabel;

    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker registeredDatePicker;

    @FXML
    private DatePicker finalizedDatePicker;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField clientTextField;
    @FXML
    private TextField itemTextField;
    @FXML
    private MenuButton statusMenuButton;


    SelectRequestScreenController selectRequestScreenController;


    private final RequestService requestService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public RequestsTabController(RequestService requestService, LoaderFactory loaderFactory) {
        super(requestService, RequestTableRow::new);
        this.requestService = requestService;
        this.loaderFactory = loaderFactory;
    }

    @Override
    protected void initTableView() {
        tableView.getColumns().clear();

        TableColumn<RequestTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<RequestTableRow, String> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        registeredDateColumn.setPrefWidth(120);
        TableColumn<RequestTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<RequestTableRow, String> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        finalizedDateColumn.setPrefWidth(120);
        TableColumn<RequestTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        descriptionColumn.setPrefWidth(130);
        TableColumn<RequestTableRow, String> resultColumn = TableColumnFactory.createColumn("Result", "result");
        resultColumn.setPrefWidth(130);
        TableColumn<RequestTableRow, String> itemColumn = TableColumnFactory.createColumn("Item", "item");
        itemColumn.setPrefWidth(130);
        TableColumn<RequestTableRow, String> clientColumn = TableColumnFactory.createColumn("Client", "client");
        clientColumn.setPrefWidth(130);

        tableView.getColumns().addAll(
                idColumn,
                registeredDateColumn,
                descriptionColumn,
                statusColumn,
                finalizedDateColumn,
                resultColumn,
                clientColumn,
                itemColumn
        );

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }


    //TODO: complex fields search
    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

        statusMenuButton.getItems().addAll(
                new CheckMenuItem("OPN"),
                new CheckMenuItem("PRO"),
                new CheckMenuItem("FIN"),
                new CheckMenuItem("CAN")
        );

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registerDate"),
                        new DatePickerParamBinding(finalizedDatePicker, "endDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(resultTextField, "result"),
                        new ConstantParamBinding("sort", "registerDate,desc"),
                        new SupplierBasedParamBinding("item.owner.id", () -> customerId),
                        new CheckMenuParamBinding(statusMenuButton, "status")
                )
        );
        statusMenuButton.setOnHidden(e -> onStatusesUpdate());


    }



    public void addRequest(ActionEvent event) throws IOException  {
        FXMLLoader loader = loaderFactory.load("/fxml/addRequestScreen.fxml");

        Parent addRequestScreen = loader.load();

        Scene nextScene = new Scene(addRequestScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    public void updateRequest(ActionEvent event) {
        try{
            FXMLLoader loader = loaderFactory.load("/fxml/updateRequestScreen.fxml");

            Parent updateRequestScreen = loader.load();
            Scene nextScene = new Scene(updateRequestScreen);
            UpdateRequestScreenController addRequestScreenController = loader.getController();
            addRequestScreenController.setRequest(requestService.findById(getCurrentSelection().getId()));
            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){}

    }


    public void finalizeRequest(ActionEvent event){

        RequestTableRow selection = tableView.getSelectionModel().getSelectedItem();

        if (selection != null){
            try
            {
                FXMLLoader loader = loaderFactory.load("/fxml/finalizeRequestScreen.fxml");

                Parent detailsScreen = loader.load();
                FinalizeRequestScreenController dsc = loader.getController();

                dsc.setRequestTableRow(selection);

                Scene nextScene = new Scene(detailsScreen);

                Stage window = new Stage();

                window.setScene(nextScene);
                window.setResizable(false);
                window.show();
            }
            catch (IOException e)
            {}
        }

    }


    public void showRequestDetails(ActionEvent event) {
        try
        {
            FXMLLoader loader = loaderFactory.load("/fxml/detailsScreen.fxml");

            Parent detailsScreen = loader.load();
            DetailsScreenController dsc = loader.getController();
            RequestEntity re = requestService.findById(getCurrentSelection().getId());
            dsc.setText(re.getDescription(),re.getResult());
            Scene nextScene = new Scene(detailsScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();
        }
        catch (IOException e)
        {}
    }


    public void manageRequestActivities(ActionEvent event) throws IOException  {


        RequestTableRow selection = tableView.getSelectionModel().getSelectedItem();
        if (selection!= null){


            FXMLLoader loader = loaderFactory.load("/fxml/manageActivitiesScreen.fxml");
            Parent manageActivitiesScreen = loader.load();

            ManageActivitiesScreenController manageActivitiesScreenController = loader.getController();

            manageActivitiesScreenController.setRequestId(selection.getId());
            manageActivitiesScreenController.addParamBindings(new ConstantParamBinding("request.id", selection.getId()));

            Scene nextScene = new Scene(manageActivitiesScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();

        }
    }

    @FXML
    private void clearRegisterDateButtonClicked(ActionEvent event) {
        registeredDatePicker.setValue(null);
    }

    @FXML
    private void clearFinalizedDateButtonClicked(ActionEvent event) {
        finalizedDatePicker.setValue(null);
    }

    public void onStatusesUpdate() {

        var joiner = new StringJoiner( ", ");
        joiner.setEmptyValue("");
        for (var item : statusMenuButton.getItems().filtered(it -> it instanceof CheckMenuItem)){
            var checkItem = (CheckMenuItem) item;
            if (checkItem.isSelected()){
                joiner.add(checkItem.getText());
            }
        }
        selectedStatusesLabel.setText(joiner.toString());
    }

    private CustomerTableRow queryCustomer;
    private String customerId = "";

    @FXML
    private void selectCustomerButtonClicked(ActionEvent actionEvent) throws IOException{

        FXMLLoader loader = loaderFactory.load("/fxml/selectCustomerScreen.fxml");
        Parent selectCustomerScreen = loader.load();

        SelectCustomerScreenController selectCustomerScreenController = loader.getController();

        var selectCustomerScene = new Scene(selectCustomerScreen);

        Stage window = new Stage();

        window.setScene(selectCustomerScene);
        window.setResizable(false);
        window.showAndWait(); //wait for results from SelectCustomerScreen

        queryCustomer = selectCustomerScreenController.getCurrentSelection();
        if (queryCustomer != null){
            selectedCustomerHyperlink.setVisible(true);
            selectedCustomerHyperlink.setText(queryCustomer.getFirstName() + " " + queryCustomer.getLastName());
            customerId = queryCustomer.getId();
        }
    }

    @FXML
    private void selectItemButtonClicked(ActionEvent actionEvent) {


    }

    @FXML
    private Hyperlink selectedCustomerHyperlink;

    @FXML
    private void clearSelectedCustomer(ActionEvent actionEvent) {
        selectedCustomerHyperlink.setVisible(false);
        selectedCustomerHyperlink.setText("");
        customerId = "";
    }
}
