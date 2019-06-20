package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.activity.ActivityTableRow;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.DatePickerParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class WorkerMainScreenController {



    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
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
    private TextField clientTextField;
    @FXML
    private TextField itemTextField;


    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    private TableView<RequestTableRow> WorkerTableView;



    private final RequestService requestService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public WorkerMainScreenController(RequestService requestService, LoaderFactory loaderFactory) {
        this.requestService = requestService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        WorkerTableView.getColumns().clear();

        TableColumn<RequestTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<RequestTableRow, LocalDateTime> registeredDateColumn = TableColumnFactory.createColumn("Registered Date", "registeredDate");
        TableColumn<RequestTableRow, String> statusColumn = TableColumnFactory.createColumn("Status", "status");
        TableColumn<RequestTableRow, LocalDateTime> finalizedDateColumn = TableColumnFactory.createColumn("Finalized Date", "finalizedDate");
        TableColumn<RequestTableRow, String> descriptionColumn = TableColumnFactory.createColumn("Description", "description");
        TableColumn<RequestTableRow, String> resultColumn = TableColumnFactory.createColumn("Result", "result");
        TableColumn<RequestTableRow, CustomerEntity> clientColumn = TableColumnFactory.createColumn("Client", "client");

        WorkerTableView.getColumns().addAll(
                idColumn,
                registeredDateColumn,
                statusColumn,
                finalizedDateColumn,
                descriptionColumn,
                clientColumn,
                resultColumn
        );

        for (var column : WorkerTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new DatePickerParamBinding(registeredDatePicker, "registeredDate"),
                        new TextFieldParamBinding(statusTextField, "statusText"),
                        new DatePickerParamBinding(finalizedDatePicker, "finalizedDate"),
                        new TextFieldParamBinding(clientTextField, "client"),
                        new TextFieldParamBinding(itemTextField, "item")
                )
        );
    }

    private void initPagination() {
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);
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
    private void updateTable() {
        try{
            Page<RequestEntity> page = requestService.findAllMatching(uriSearchQuery, pagination.getCurrentPageIndex(), rowsPerPage);
            pagination.setPageCount((int) page.getTotalPages());
            WorkerTableView.getItems().clear();


            for (RequestEntity request: page.getResources()) {
                WorkerTableView.getItems().add(new RequestTableRow(request));
            }
        } catch (ResourceAccessException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.show();
        }

    }



    @FXML
    private void finalizeWorkerButtonClicked()  {

    } @FXML
    private void cancelWorkerButtonClicked()  {

    }

    @FXML
    private void showWorkerButtonClicked() {

    }
    @FXML
    private void updateWorkerButtonClicked() {

    }



}
