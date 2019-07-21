package pl.polsl.repairmanagementdesktop.controllers;

import javafx.application.Platform;
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
import pl.polsl.repairmanagementdesktop.model.item.ItemEntity;
import pl.polsl.repairmanagementdesktop.model.item.ItemService;
import pl.polsl.repairmanagementdesktop.model.item.ItemTableRow;
import pl.polsl.repairmanagementdesktop.model.itemtype.ItemTypeEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Scope("prototype")
@Controller
public class ItemsTabController {


    private final LoaderFactory fxmlLoaderFactory;


    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField rowsPerPageTextField;
    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;


    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField clientTextField;


    @FXML
    private TableView<ItemTableRow> itemTableView;// = new TableView<ItemTableRow>();


    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    private final ItemService itemService;

    @Autowired
    public ItemsTabController(ItemService itemService,LoaderFactory fxmlLoaderFactory) {

        this.fxmlLoaderFactory = fxmlLoaderFactory;
        this.itemService = itemService;

        currentResources.setSize(1);

    }



    private void initItemsTableView() {
        itemTableView.getColumns().clear();

        TableColumn<ItemTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<ItemTableRow, String> nameColumn = TableColumnFactory.createColumn("Name", "name");
        TableColumn<ItemTableRow, String> typeColumn = TableColumnFactory.createColumn("Type", "type");
        TableColumn<ItemTableRow, String> clientColumn = TableColumnFactory.createColumn("Client", "client");

        itemTableView.getColumns().addAll(
                idColumn,
                nameColumn,
                typeColumn,
                clientColumn
        );

        for (var column : itemTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    public void addParamBindings(ParamBinding... bindings){
        for (var binding : bindings){
            uriSearchQuery.getBindings().add(binding);
        }
        uriSearchQuery.update();
    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(nameTextField, "name"),
                        new TextFieldParamBinding(typeTextField, "type.type"),
                        new TextFieldParamBinding(clientTextField, "client")
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

//    @FXML
//    public void initialize() {
//        initItemsTableView();
//        initQueryFields();
//        initPagination();
//
//    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
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
    private void showItemsButtonClicked() {

        if (!isUpdating){
            executor.submit(() -> {

                rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
                uriSearchQuery.update();

                currentResources.clear();
                Page<ItemEntity> firstPage = itemService.findAllMatching(uriSearchQuery, 0, rowsPerPage);
                currentResources.setSize((int) firstPage.getTotalPages());

                currentResources.set(0, firstPage.getResources().stream().map(ItemTableRow::new).collect(Collectors.toList()));
                Platform.runLater(() -> {
                    pagination.setPageCount(-1); //workaround to force pageFactory call
                    pagination.setPageCount((int) firstPage.getTotalPages());
                });
            });
        }
    }


    private boolean isUpdating = false;

    Vector<List<ItemTableRow>> currentResources = new Vector<>();

    @Autowired
    ThreadPoolExecutor executor;


    private void updateTable() {
        if (!isUpdating) {
            itemTableView.getItems().clear();
            isUpdating = true;
            int currentIndex = pagination.getCurrentPageIndex();
            var page = currentResources.get(currentIndex);
            if (page == null) {
                currentResources.set(currentIndex, itemService
                        .findAllMatching(uriSearchQuery, currentIndex, rowsPerPage)
                        .getResources().stream().map(ItemTableRow::new)
                        .collect(Collectors.toList())
                );
                page = currentResources.get(currentIndex);
            }
            var resourcesIt = page.iterator();
            if (resourcesIt.hasNext()){
                itemTableView.getItems().add(resourcesIt.next());
                executor.submit(() -> {
                    try {
                        resourcesIt.forEachRemaining(entity -> Platform.runLater(() -> {
                            itemTableView.getItems().add(entity);
                        }));
                    } catch (ResourceAccessException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Connection error");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.show();
                    } finally {
                        isUpdating = false;
                    }

                });
            }
        }
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

    private boolean isInitialized = false;

    public void initView() {
        if (!isInitialized){
            initItemsTableView();
            initQueryFields();
            initPagination();
            isInitialized = true;
        }
    }

    ItemTableRow getCurrentSelection(){
        return itemTableView.getSelectionModel().getSelectedItem();
    }
}
