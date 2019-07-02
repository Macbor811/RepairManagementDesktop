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
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.*;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class RequestsTabController {



    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;

    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;

    @FXML
    private TextField idTextField;
    @FXML
    private DatePicker registeredDatePicker;
    @FXML
    private TextField statusTextField;
    @FXML
    private DatePicker finalizedDatePicker;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField clientTextField;
    @FXML
    private TextField itemTextField;

    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();


    SelectRequestScreenController selectRequestScreenController;

    @FXML
    private TableView<RequestTableRow> requestTableView;

    private final RequestService requestService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public RequestsTabController(RequestService requestService, LoaderFactory loaderFactory) {
        this.requestService = requestService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        requestTableView.getColumns().clear();

        TableColumn<RequestTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<RequestTableRow, String> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        TableColumn<RequestTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<RequestTableRow, String> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        TableColumn<RequestTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        TableColumn<RequestTableRow, String> resultColumn = TableColumnFactory.createColumn("Result", "result");
        TableColumn<RequestTableRow, String> itemColumn = TableColumnFactory.createColumn("Item", "item");
        TableColumn<RequestTableRow, String> clientColumn = TableColumnFactory.createColumn("Client", "client");

        requestTableView.getColumns().addAll(
                idColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                descriptionColumn,
                clientColumn,
                itemColumn,
                resultColumn
        );

        for (var column : requestTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registerDate"),
                        new TextFieldParamBinding(statusTextField, "status"),
                        new DatePickerParamBinding(finalizedDatePicker, "endDate"),
                        new TextFieldParamBinding(descriptionTextField, "description"),
                        new TextFieldParamBinding(clientTextField, "client"),
                        new TextFieldParamBinding(itemTextField, "item.name")
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
        initActivityTableView();
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


    /**
     * Updates search settings from text fields to show new results.
     */
    @FXML
    private void showRequestButtonClicked() {
        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        uriSearchQuery.update();
        updateTable();
    }


    public void addRequest(ActionEvent event) throws IOException  {
        FXMLLoader loader = loaderFactory.load("/fxml/addRequestScreen.fxml");

        Parent managerMainScreen = loader.load();

        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

    public void updateRequest(ActionEvent event) {
        //TODO
    }

    public void finalizeRequest(ActionEvent event){
        //TODO
    }
    public void showRequestDetails(ActionEvent event) {
        //TODO
    }

    public void manageRequestActivities(ActionEvent event) throws IOException  {


        RequestTableRow selection = requestTableView.getSelectionModel().getSelectedItem();
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

    private void updateTable() {
        try{
            Page<RequestEntity> page = requestService.findAllMatching(uriSearchQuery, pagination.getCurrentPageIndex(), rowsPerPage);
            pagination.setPageCount((int) page.getTotalPages());
            requestTableView.getItems().clear();


            for (RequestEntity request: page.getResources()) {
                requestTableView.getItems().add(new RequestTableRow(request));
            }
        } catch (ResourceAccessException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.show();
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

    RequestTableRow getCurrentSelection(){
        return requestTableView.getSelectionModel().getSelectedItem();
    }

}
